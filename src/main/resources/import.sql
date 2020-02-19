
insert into category (id, name, description) values (NEXTVAL('category_seq'), 'Cadernos e Agendas', 'Cadernos universitários, cartografia, fichários, etc.');
insert into category (id, name, description) values (NEXTVAL('category_seq'), 'Escrita e Corretivos', 'Canetas, lápis, apontadores, borrachas, corretivos, marca-texto,etc.');
insert into category (id, name, description) values (NEXTVAL('category_seq'), 'Arte e Pintura', 'Canetas hidrográficas, colas, lápis de cor, tesouras, etc');
insert into category (id, name, description) values (NEXTVAL('category_seq'), 'Mochilas e Lancheiras', 'Estojos, lancheiras, mochilas e bolsas em geral.');
insert into category (id, name, description) values (NEXTVAL('category_seq'), 'Papéis e Pastas', 'Papel almaço, papel sulfite, pastas, envelopes, etc.');
insert into category (id, name, description) values (NEXTVAL('category_seq'), 'Eletrônicos', 'Pen drive, tablet, projetor, etc.');
insert into category (id, name, description) values (NEXTVAL('category_seq'), 'Material Técnico', 'Blocos técnicos, compassos, réguas.');
insert into category (id, name, description) values (NEXTVAL('category_seq'), 'Professores', 'Aventais, diários de classe, apagadores, pilot para quadro branco, etc.');

insert into product (barcode, name, description, quantity, category_id) values ('1234567891011121314151617', 'Caderno 96 fls', 'Caderno com espiral de 96 fls', 50, select id from category where name = 'Cadernos e Agendas');
insert into product (barcode, name, description, quantity, category_id) values ('1234567891011121314151618', 'Mochila para notebook 17" ', 'Mochila para notebook de até 17 polegadas.', 2, select id from category where name = 'Mochilas e Lancheiras');
insert into product (barcode, name, description, quantity, category_id) values ('1234567891011121314151619', 'Caneta esferográfica preta', 'Caneta esferográfica preta', 100, select id from category where name = 'Escrita e Corretivos');
insert into product (barcode, name, description, quantity, category_id) values ('1234567891011121314151620', 'Corretivo de caneta', 'Corretivo para escrita de caneta', 20, select id from category where name = 'Escrita e Corretivos');

