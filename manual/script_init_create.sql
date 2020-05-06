create table genre
(
	genre_id bigint auto_increment
		primary key,
	name varchar(255) not null
)
;

create table hall
(
	hall_id bigint auto_increment
		primary key,
	capacity smallint null,
	name varchar(255) null,
	description varchar(1000) null
)
;

create table movie
(
	movie_id bigint auto_increment
		primary key,
	description varchar(2500) null,
	time_length int null,
	title varchar(255) null,
	issue_year smallint null
)
;

create table movie_genre
(
	movie_id bigint not null,
	genre_id bigint not null,
	constraint FK86p3roa187k12avqfl28klp1q
		foreign key (genre_id) references genre (genre_id),
	constraint FKp6vjabv2e2435at1hnuxg64yv
		foreign key (movie_id) references movie (movie_id)
)
;

create table role
(
	role_id bigint auto_increment
		primary key,
	name varchar(255) not null
)
;

create table session
(
	session_id bigint auto_increment
		primary key,
	price decimal(19,2) null,
	start_time datetime not null,
	hall_id bigint not null,
	movie_id bigint not null,
	constraint FKd38hyauvpulqx795f234oe049
		foreign key (movie_id) references movie (movie_id),
	constraint FKdkbjq18dsn41qptljosa4cxk1
		foreign key (hall_id) references hall (hall_id)
)
;

create table user_info
(
	info_id bigint auto_increment
		primary key,
	about varchar(400) null,
	genres varchar(400) null,
	movies varchar(400) null
)
;

create table user
(
	user_id bigint auto_increment
		primary key,
	password varchar(255) null,
	username varchar(255) not null,
	email varchar(50) null,
	info_id bigint null,
	balance decimal(19,2) null,
	constraint UK_sb8bbouer5wak8vyiiy4pf2bx
		unique (username),
	constraint FKi36jgqhfm6cbngbaw77o3ob42
		foreign key (info_id) references user_info (info_id)
)
;

create table friend_request
(
	id bigint auto_increment
		primary key,
	status varchar(2) not null,
	user_request bigint not null,
	user_response bigint not null,
	constraint UKedrnth4x9b57relb1jyyji4dp
		unique (user_request, user_response),
	constraint FKcmbs9s8klw2v9cfrsh49ng1it
		foreign key (user_request) references user (user_id),
	constraint FKkhtqqcr7k8hixbmnh17mvocli
		foreign key (user_response) references user (user_id)
)
;

create table movie_rate
(
	rate_id bigint auto_increment
		primary key,
	value int null,
	movie_id bigint not null,
	user_id bigint not null,
	constraint FKgrbs6q0o6p4kglpgavol00scl
		foreign key (user_id) references user (user_id),
	constraint FKi7otfnajcc767xr87eabr6f65
		foreign key (movie_id) references movie (movie_id)
)
;

create table ticket
(
	ticket_id bigint auto_increment
		primary key,
	user_by_id bigint not null,
	user_for_id bigint not null,
	session_id bigint not null,
	constraint FK5x0dvk4itqbdpu2dxdo02b5f7
		foreign key (session_id) references session (session_id),
	constraint FKm2v6ffadhnp23wwwubnwc31p9
		foreign key (user_for_id) references user (user_id),
	constraint FKp0emnkj55v2a2teta0aui2qdq
		foreign key (user_by_id) references user (user_id)
)
;

create table user_black_list
(
	user_id bigint not null,
	blocked_user_id bigint not null,
	constraint FK5cid0ow1fbgihana0empaj62o
		foreign key (user_id) references user (user_id),
	constraint FK5py203rfumsgludkrs2vl70jg
		foreign key (blocked_user_id) references user (user_id)
)
;

create table user_friends
(
	user_id bigint not null,
	friend_id bigint not null,
	constraint FK9i7cldnk7cx2g47qex8ovm2ah
		foreign key (user_id) references user (user_id),
	constraint FKm24u3115vx7bnje3b09oyflkd
		foreign key (friend_id) references user (user_id)
)
;

create table user_role
(
	user_id bigint not null,
	role_id bigint not null,
	primary key (user_id, role_id),
	constraint FK859n2jvi8ivhui0rl0esws6o
		foreign key (user_id) references user (user_id),
	constraint FKa68196081fvovjhkek5m97n3y
		foreign key (role_id) references role (role_id)
)
;

