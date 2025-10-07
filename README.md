# atlasbase-core
Core Backend Platform of Project Atlas


# How to run application
1. Set up Database connection. Run `docker-compose.yaml` through IDE or terminal: `docker compose up -d` on the root directory. (Make sure Docker is running on local)


# Liquibase
Upon application startup, tables will be created based on `rollout` folder and can be manually rolled-back under `rollback` folder.

To manually update: `mvn liquibase:update`
To manually rollback: `mvn liquibase:rollback -`