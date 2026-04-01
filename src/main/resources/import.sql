
insert into artista (nome,data_fundacao,pais_origem,ativo) values ('Legião Urbana','1983-05-02','Brasil',false);
insert into artista (nome,data_fundacao,pais_origem,ativo) values ('Oasis','1997-02-09','Inglaterra',true);
insert into artista (nome,data_fundacao,pais_origem,ativo) values ('Iron Maiden','1975-09-01','Inglaterra',true);
insert into artista (nome,data_fundacao,pais_origem,ativo) values ('ACDC','1979-11-07','Australia',true);
insert into artista (nome,data_fundacao,pais_origem,ativo) values ('Megadeth','1983-08-03','EUA',true);

insert into integrante (fk_artista,nome,data_nascimento,funcao) values (1,'Renato Russo','1955-08-01','VOCALISTA'); 
insert into integrante (fk_artista,nome,data_nascimento,funcao) values (2,'Liam Gallagher','1977-05-09','VOCALISTA'); 
insert into integrante (fk_artista,nome,data_nascimento,funcao) values (3,'Bruce Dickinson','1952-02-01','GUITARRISTA'); 
insert into integrante (fk_artista,nome,data_nascimento,funcao) values (4,'Phil Rudd','1947-03-02','BATERISTA'); 
insert into integrante (fk_artista,nome,data_nascimento,funcao) values (5,'Kiko Loureiro','1981-12-31','GUITARRISTA'); 

insert into musica (fk_artista,titulo,data_lancamento,duracao,genero) values (1,'Faroeste Caboclo','1983-01-01',9.56,'Rock');
insert into musica (fk_artista,titulo,data_lancamento,duracao,genero) values (2,'Live Forever','1999-12-01',3.23,'Rock');
insert into musica (fk_artista,titulo,data_lancamento,duracao,genero) values (3,'Run to The Hills','1978-11-04',8.17,'Metal');
insert into musica (fk_artista,titulo,data_lancamento,duracao,genero) values (4,'Back in Black','1974-07-05',7.28,'Rock');
insert into musica (fk_artista,titulo,data_lancamento,duracao,genero) values (5,'Tipping Point','1987-04-02',6.35,'Metal');

