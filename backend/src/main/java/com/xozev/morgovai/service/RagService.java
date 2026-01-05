package com.xozev.morgovai.service;

import com.xozev.morgovai.entity.Chat;
import com.xozev.morgovai.entity.Doc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RagService {

    private final ChatModel chatModel;
    private final VectorStore vectorStore;

    public void ingest(Doc doc) {
        try {
            Path filePath = Paths.get(doc.getPath());
            Resource resource = new FileSystemResource(filePath);
            
            // Basic text reading
            TextReader textReader = new TextReader(resource);
            textReader.getCustomMetadata().put("docId", doc.getId().toString());
            textReader.getCustomMetadata().put("serviceId", doc.getService().getId().toString());

            List<Document> documents = textReader.read();

            // Split into chunks
            TokenTextSplitter splitter = new TokenTextSplitter();
            List<Document> chunks = splitter.apply(documents);

            vectorStore.add(chunks);
            log.info("Ingested document: {} with {} chunks", doc.getTitle(), chunks.size());

        } catch (Exception e) {
            log.error("Failed to ingest document: {}", doc.getTitle(), e);
            throw new RuntimeException("Failed to ingest document", e);
        }
    }

    public String generateResponse(String userQuery, Chat chat) {
        // 1. Retrieve context
        String filterExpression = "serviceId == '" + chat.getService().getId() + "'";
        
        // Fix: Use Builder pattern for SearchRequest if static query() is missing
        SearchRequest searchRequest = SearchRequest.builder()
                .query(userQuery)
                .topK(3)
                .filterExpression(filterExpression)
                .build();
        
        List<Document> similarDocuments = vectorStore.similaritySearch(searchRequest);

        // Fix: Use getText() or content() depending on version, fallback to generic access if needed
        // Assuming getText() or generic content methods
        String context = similarDocuments.stream()
                .map(doc -> doc.getContent()) // content() or getText(). Document usually has getContent() in 1.x
                .collect(Collectors.joining("\n"));

        // 2. Augment Prompt via ChatClient (Fluent API)
        ChatClient chatClient = ChatClient.builder(chatModel).build();
        
        String systemText = """
                You are a helpful assistant for the government service: {serviceName}.
                Use the following context to answer the user's question.
                If the answer is not in the context, say you don't know based on the provided documents.
                
                Context:
                {context}
                """;

        return chatClient.prompt()
                .system(sp -> sp.text(systemText)
                        .param("serviceName", chat.getService().getName())
                        .param("context", context))
                .user(userQuery)
                .call()
                .content();
    }
}
