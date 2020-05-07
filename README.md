<h3>Добро пожаловать в BuddiesMovie</h2>

Для полноценного использования сервиса необходимо  создать схему movie_friend_schema и запустить скрипт по добавлению таблиц (<I>manual/script_init_create.sql</I>) 
и данных (<I>manual/script_init_insert.sql</I>).<br>
Также необходимо настроить подключние к базе данных spring.datasource.url, spring.datasource.username,
spring.datasource.password (файл application.properties).

Для удобства уже создан один пользователь-администратор с характерным именем <b>admin</b> и невероятно крепким паролем <b>hardadminpassword666</b>.

<h4>Что может делать админстратор</h4>

<h5>Administrators management</h5>
Управление пользователями посредством установки или снятия с пользователя роли администратора.
Пользователь с активной жизненной позицией в данном сервисе (с друзьями, остатком, билетами и т.д.) не может стать администратором.
Администратор не может снять с себя права администратора, иначе их может совсем не остаться (<i>не пытайтесь это сделать</i>).

<h5>Movies management</h5>
Управление фильмами. Управление жанрами добавлено тут же.

<h5>Session management</h5>
Управление сеансами и залами. При добавлении сеанса учитывается время проведения других сеансов в рамках зала.
Файл application.properties:
<i>session.time.before.hour</i><br>
Можно настроить пограничное время, до когорого можно добавлять сеанс, чтобы посетители
всё же смогли заранее купить билеты и показ не был убыточен.
<i>session.time.between.min</i><br>
Между показами должно пройти время, чтобы в залах прибрали и были готовы принимать новых посетителей.

<h5>Load movies</h5>
Для того, чтобы не добавлять фильмы вручную, добавлен сервис для выгрузки фильмов с сайта kinogo.by с использованием jsoup. Выгрузка заключается в парсинге страниц
с премьерами, добавлен и доработан из домашнего задания первой части курса (Java Core). Добавлена проверка на существующие в базе фильмы,
поэтому повторно они выгружены не будут.

На этом полномочия администратора всё.

<h4>Что может делать пользователь</h4>

<h5>My profile</h5>
Просмотр личной страницы и управление аккаунтом. 
<h6>Edit my info</h6>
Придание своей странице индивидуаьность, описание себя и своих предпочтений.
<h6>My tickets</h6>
Просмотр билетов пользователя, разбитые по категориям. До севнса пользователь может вернуть билет (не важно, купил он сам или его друг), но только
если еще не слишком поздно. 
<h6>My friends</h6>
Просмотр входящих заявок (которые можно принять или отклонить), исходящих (которые можно отменить) заявок и текущих друзей (которых всегда можно удалить или отправить в черный список).
<h6>Top up balance</h6>
 Чтобы покупать билеты, нужны деньги на балансе. Укажите любую (валидную) сумму и она прибивится к вашему балансу.

<i>ticket.return.max.before</i><br>
Максимальное время, до которого пользователь может вернуть билет.

После просмотра пользователь может поставить оценку в настроенных пределах <i>movie.min.rate</i> и <i>movie.max.rate</i>.


<h5>Find sessions</h5>
Просмотр всех будущих сеансов с возможностью купить на них билеты (если они еще остались). 
<h6>Show nearest sessions</h6>
Сеансы на ближайшие n дней, где n настраивается в <i>session.togay.count</i>.


<h5>Find movies</h5>
Все фильмы, которые можно найти в сервисе. Можно переходить на страницу фильма и сразу увидеть будущие сеансы, искать по дате или интересующему жанру, тыкнув на нужный.

<h5>Find friends</h5>
Найти друзей, чтобы покупать друг другу билеты, вместе ходить в кино и после сеанса обсудить полученные эмоции.
<br>
<br>
<i><u>Указание по управлению: </u></i>На формах, где возможен мультивыбор (выбор жанра фильма или пользователи, для которых купить билеты) осуществляется посредством зажатия клавиши ctrl
