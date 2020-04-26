# art-gallery
Multiproject repo to handle Art Gallery microservices ecosystem

    - art-api (to handle Arts, Commentaries, Likes)
    - user-api (which hold users' logins - the plan is to use Oauth2 for end-users, to not bother with fragile data)
    - security-api (to handle clients' authentication and authorization, clients = internal services)
    - web application for end-users
