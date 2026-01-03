actors:
- admin
- users (no login)

use cases:

  admin:
    - auth
    - crud gov service
    - crud service docs
  
  user:
    - send query
    - feedback (like/dislike)


entities:
- base
  id
  createdat
  updatedat
  deletedat

- users
  username
  password
  email

- services
  name


- docs
  title
  path
  size
  lang
  service id


- chat
  title
  service id

- user_message
  chat id
  text


- llm_response
  chat id
  text
  feedback