-- Titre             : Script SQL (PostgreSQL) de création de la base de données du projet bibliothèque
-- Version           : 1.0
-- Date création     : 07 mars 2006
-- Date modification : 9 avril 2017
-- Auteur            : Philippe TANGUY
-- Description       : Ce script est une ébauche, à compléter, qui permet la création de la table
--                     "livre" pour la réalisation de la fonctionnalité "liste de tous les livres".

-- +----------------------------------------------------------------------------------------------+
-- | Suppression des tables                                                                       |
-- +----------------------------------------------------------------------------------------------+

drop table if exists "livre";

-- +----------------------------------------------------------------------------------------------+
-- | Création des tables                                                                          |
-- +----------------------------------------------------------------------------------------------+

create table livre
(
  id     serial primary key,
  isbn10 varchar(25) unique,
  isbn13 varchar(25) unique,
  titre  varchar(50) not null,
  auteur varchar(30)
);

-- +----------------------------------------------------------------------------------------------+
-- | Insertion de quelques données de pour les tests                                              |
-- +------------------------------------------------------Abonne----------------------------------------+

insert into livre values(nextval('livre_id_seq'), '2-84177-042-7', NULL,                'JDBC et JAVA',                            'George REESE');    -- id = 1
insert into livre values(nextval('livre_id_seq'), NULL,            '978-2-7440-7222-2', 'Sociologie des organisations',            'Michel FOUDRIAT'); -- id = 2
insert into livre values(nextval('livre_id_seq'), '2-212-11600-4', '978-2-212-11600-7', 'Le data warehouse',                       'Ralph KIMBALL');   -- id = 3
insert into livre values(nextval('livre_id_seq'), '2-7117-4811-1', NULL,                'Entrepots de données',                    'Ralph KIMBALL');   -- id = 4
insert into livre values(nextval('livre_id_seq'), '2012250564',    '978-2012250567',    'Oui-Oui et le nouveau taxi',              'Enid BLYTON');     -- id = 5
insert into livre values(nextval('livre_id_seq'), '2203001011',    '978-2203001015',    'Tintin au Congo',                         'HERGÉ');           -- id = 6
insert into livre values(nextval('livre_id_seq'), '2012011373',    '978-2012011373',    'Le Club des Cinq et le trésor de l''île', 'Enid BLYTON');     -- id = 7


Abonne

create table Exemplaire
(
  idExemp serial primary key,
  id int
);

ALTER TABLE Exemplaire 
ADD CONSTRAINT c_Exemp_id_fk FOREIGN KEY (id) REFERENCES livre (id);


create table Abonne
(
  id_abonne     serial primary key,	
  Nom varchar(10),
  Prenom varchar(15),
  statut  varchar(10) not null,
  Adresse_email varchar(50)
);


create table emprunts
(
idExemp int,
id_abonne int,
status varchar default 'Borrowed',
Date_emprunt timestamp not null default now(),
Date_retourne timestamp
)

ALTER TABLE emprunts 
ADD CONSTRAINT c_emprunts_exemp_fk FOREIGN KEY (idexemp) REFERENCES exemplaire (idexemp);



ALTER TABLE emprunts 
ADD CONSTRAINT c_abonne_id_fk FOREIGN KEY (id_abonne) REFERENCES abonne (id_abonne);

update emprunts  set  Date_retourne = (now()::DATE - 1), status = 'Returned'  where  idExemp = 6

