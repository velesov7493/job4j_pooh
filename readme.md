[![Build Status](https://app.travis-ci.com/velesov7493/job4j_pooh.svg?branch=master)](https://app.travis-ci.com/velesov7493/job4j_pooh)
## Простой базовый сервис java messages (JMS) ##
Это учебный проект сервиса java messages. 
У сервиса есть 2 режима работы: queue (очередь) и topic (тема).
Каждое сообщение в очереди может быть получено только одним получателем.
Каждое сообщение в теме предназначено всем получателям.

#### Описание протокола ####

+ Добавление сообщения в очередь:

`curl -X POST -d "temperature=18" http://localhost:9000/queue/weather`

где `"temperature=18"` - тело сообщения;
`queue` - режим работы - очередь;
`weather` - имя очереди.

+ Получение сообщения из очереди:

`curl -X GET http://localhost:9000/queue/weather`

Ответ: `temperature=18`.

+ Добавление сообщения в тему:

`curl -X POST -d "temperature=18" http://localhost:9000/topic/weather`

+ Получение сообщения из темы

`curl -X GET http://localhost:9000/topic/weather/1`

где `1` - идентификатор получателя;

Ответ: `temperature=18`.

#### Технологии проекта ####

![badge](https://img.shields.io/badge/Java-14-green)
![badge](https://img.shields.io/badge/Maven-3.6-green)