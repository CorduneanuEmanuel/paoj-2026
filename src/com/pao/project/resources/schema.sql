PRAGMA foreign_keys = ON;

DROP TABLE IF EXISTS comanda_produse;
DROP TABLE IF EXISTS comenzi;
DROP TABLE IF EXISTS produse;
DROP TABLE IF EXISTS livratori;
DROP TABLE IF EXISTS restaurante;
DROP TABLE IF EXISTS clienti;
DROP TABLE IF EXISTS firme;

create table firme (
    cui integer primary key,
    nr_ordine text not null,
    nume text not null
);

create table clienti (
    id integer primary key,
    nume text not null
);

create table restaurante (
    id integer primary key,
    cui_firma integer not null,
    nume text not null,
    oras text not null,
    adresa text not null,
    poz_x real not null,
    poz_y real not null,
    nr_vanzari integer not null default 0,
    foreign key (cui_firma) references firme(cui)
);

create table livratori (
    id integer primary key,
    nume text not null,
    disponibil integer not null,
    poz_x real not null,
    poz_y real not null
);

create table produse (
    id integer primary key,
    restaurant_id integer not null,
    nume text not null,
    pret real not null,
    foreign key (restaurant_id) references restaurante(id)
);

create table comenzi (
    id text primary key,
    client_id integer not null,
    restaurant_id integer not null,
    sofer_id integer,
    status text not null,
    oras_livrare text not null,
    adresa_livrare text not null,
    poz_x_livrare real not null,
    poz_y_livrare real not null,
    total real not null,
    foreign key (client_id) references clienti(id),
    foreign key (restaurant_id) references restaurante(id),
    foreign key (sofer_id) references livratori(id)
);

create table comanda_produse (
    comanda_id text not null,
    produs_id integer not null,
    cantitate integer not null,
    primary key (comanda_id, produs_id),
    foreign key (comanda_id) references comenzi(id),
    foreign key (produs_id) references produse(id)
);


insert into firme(cui, nr_ordine, nume) values (1001, 'ro1001', 'firma a');
insert into firme(cui, nr_ordine, nume) values (1002, 'ro1002', 'firma b');

insert into clienti(id, nume) values (1, 'ion popescu');
insert into clienti(id, nume) values (2, 'maria ionescu');

insert into restaurante(id, cui_firma, nume, oras, adresa, poz_x, poz_y, nr_vanzari) values (10, 1001, 'Pizza Verde', 'Bucuresti', 'Strada a, 10', 44.43, 26.10, 0);
insert into restaurante(id, cui_firma, nume, oras, adresa, poz_x, poz_y, nr_vanzari) values (11, 1002, 'Burger RO', 'Cluj', 'Calea b, 5', 46.76, 23.58, 0);

insert into livratori(id, nume, disponibil, poz_x, poz_y) values (200, 'Andrei Marin', 1, 44.45, 26.12);
insert into livratori(id, nume, disponibil, poz_x, poz_y) values (201, 'Elena Radu', 1, 46.77, 23.60);

insert into produse(id, restaurant_id, nume, pret) values (100, 10, 'Pizza margherita', 25.0);
insert into produse(id, restaurant_id, nume, pret) values (101, 10, 'Salata caesar', 18.5);
insert into produse(id, restaurant_id, nume, pret) values (110, 11, 'Burger clasic', 20.0);

insert into comenzi(id, client_id, restaurant_id, sofer_id, status, oras_livrare, adresa_livrare, poz_x_livrare, poz_y_livrare, total) values ('c1', 1, 10, 200, 'placata', 'bucuresti', 'strada client, 1', 44.46, 26.13, 43.5);
insert into comenzi(id, client_id, restaurant_id, sofer_id, status, oras_livrare, adresa_livrare, poz_x_livrare, poz_y_livrare, total) values ('c2', 2, 11, null, 'in_asteptare', 'cluj', 'strada client, 2', 46.78, 23.61, 40.0);

insert into comanda_produse(comanda_id, produs_id, cantitate) values ('c1', 100, 1);
insert into comanda_produse(comanda_id, produs_id, cantitate) values ('c1', 101, 1);
insert into comanda_produse(comanda_id, produs_id, cantitate) values ('c2', 110, 2);
