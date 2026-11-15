insert into bolnica(id, naziv, adresa, budzet)
values (nextval('bolnica_seq'), 'KBC Vojvodina', 'Hajduk Veljkova 1', 500000),
 (nextval('bolnica_seq'), 'VMA', 'Crnotravska 17', 850000),
 (nextval('bolnica_seq'), 'Institut za kardiovaskularne bolesti', 'Put doktora Goldmana 4', 1200000),
 (nextval('bolnica_seq'), 'Dečija bolnica', 'Hajduk Veljkova 10', 450000);


insert into dijagnoza(id, naziv, opis, oznaka)
values (nextval('dijagnoza_seq'), 'Grip', 'Sezonski virus', 'J11'),
(nextval('dijagnoza_seq'), 'Aritmija', 'Nepravilan rad srca', 'I49'),
(nextval('dijagnoza_seq'), 'Bronhitis', 'Upala disajnih puteva', 'J20'),
(nextval('dijagnoza_seq'), 'Lom ruke', 'Fraktura podlaktice', 'S52');


-- Brojevi 1, 2 na kraju su ID-jevi bolnica gore unetih
insert into odeljenje(id, naziv, lokacija, bolnica)
values (nextval('odeljenje_seq'), 'Kardiologija', 'Sprat 1', 1),
(nextval('odeljenje_seq'), 'Hirurgija', 'Blok B', 2),
(nextval('odeljenje_seq'), 'Prijemno', 'Prizemlje', 3),
(nextval('odeljenje_seq'), 'Pedijatrija', 'Blok A', 4);


insert into pacijent(id, ime, prezime, zdr_osiguranje, datum_rodjenja, odeljenje, dijagnoza)
values (nextval('pacijent_seq'), 'Marko', 'Markovic', true, to_date('15.05.1990.', 'dd.mm.yyyy.'), 1, 1),
(nextval('pacijent_seq'), 'Jelena', 'Jovanović', true, to_date('22.11.1985.', 'dd.mm.yyyy.'), 1, 1),
(nextval('pacijent_seq'), 'Nikola', 'Tarić', false, to_date('05.03.2012.', 'dd.mm.yyyy.'), 3, 2),
(nextval('pacijent_seq'), 'Milica', 'Petrović', true, to_date('10.01.1998.', 'dd.mm.yyyy.'), 2, 3);

