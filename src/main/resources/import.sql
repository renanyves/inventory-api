insert into Category (name, description) values ('Cadernos e Agendas', 'Cadernos universitários, cartografia, fichários, etc.');
insert into Category (name, description) values ('Escrita e Corretivos', 'Canetas, lápis, apontadores, borrachas, corretivos, marca-texto,etc.');
insert into Category (name, description) values ('Arte e Pintura', 'Canetas hidrográficas, colas, lápis de cor, tesouras, etc');
insert into Category (name, description) values ('Mochilas e Lancheiras', 'Estojos, lancheiras, mochilas e bolsas em geral.');
insert into Category (name, description) values ('Papéis e Pastas', 'Papel almaço, papel sulfite, pastas, envelopes, etc.');
insert into Category (name, description) values ('Eletrônicos', 'Pen drive, tablet, projetor, etc.');
insert into Category (name, description) values ('Material Técnico', 'Blocos técnicos, compassos, réguas.');
insert into Category (name, description) values ('Professores', 'Aventais, diários de classe, apagadores, pilot para quadro branco, etc.');

insert into Product (barcode, name, description, quantity, category_id) values ('1234567891011121314151617', 'Caderno 96 fls', 'Caderno com espiral de 96 fls', 50, (select id from Category where name = 'Cadernos e Agendas'));
insert into Product (barcode, name, description, quantity, category_id) values ('1234567891011121314151618', 'Mochila para notebook 17" ', 'Mochila para notebook de até 17 polegadas.', 2, (select id from Category where name = 'Mochilas e Lancheiras'));
insert into Product (barcode, name, description, quantity, category_id) values ('1234567891011121314151619', 'Caneta esferográfica preta', 'Caneta esferográfica preta', 100, (select id from Category where name = 'Escrita e Corretivos'));
insert into Product (barcode, name, description, quantity, category_id) values ('1234567891011121314151620', 'Corretivo de caneta', 'Corretivo para escrita de caneta', 20, (select id from Category where name = 'Escrita e Corretivos'));
