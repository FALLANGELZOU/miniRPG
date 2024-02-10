CREATE TABLE IF NOT EXISTS `t_player`
(
`id`   INTEGER PRIMARY KEY,
`uuid` VARCHAR(64),
`health` integer
);

create table if not exists `t_money`
(
`id` integer primary key,
`player_id` integer,
`money` integer
);