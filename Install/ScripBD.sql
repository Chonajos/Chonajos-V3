--BASE DE DATOS : choni
--USUARIO: choni
--Contraseña: choni


create table rol(
id_rol_pk number(3) not null,
nombre_rol varchar(120) not null,
descripcion_rol varchar(255),
CONSTRAINT c_id_rol_pk PRIMARY KEY (id_rol_pk)
);

--select * from rol;
CREATE SEQUENCE s_rol
INCREMENT BY 1
START WITH 1
MINVALUE 0;

create table producto(
id_producto_pk varchar(4) not null,
nombre_producto varchar (120) not null,
descripcion_producto varchar(255),
CONSTRAINT c_id_producto_pk PRIMARY KEY (id_producto_pk)
);

create table subproducto(
id_subproducto_pk varchar(8) not null,
nombre_subproducto varchar (120) not null,
descripcion_subproducto varchar(255),
url_imagen_subproducto varchar(255),
id_producto_fk number ,
CONSTRAINT c_id_subproducto_pk PRIMARY KEY (id_subproducto_pk),
CONSTRAINT c_id_producto_fk FOREIGN KEY(id_producto_fk) references producto(id_producto_pk)
);


create table tipo_empaque(
id_tipo_empaque_pk number not null,
nombre_empaque varchar (120),
estatus_empaque char(1),
CONSTRAINT c_id_tipo_empaque_pk PRIMARY KEY (id_tipo_empaque_pk)
);

CREATE SEQUENCE s_tipo_empaque
INCREMENT BY 1
START WITH 1
MINVALUE 0;

 
create table tipo_venta(
id_tipo_venta_pk number not null,
nombre_tipo_venta varchar (120),
CONSTRAINT c_id_tipo_venta_pk PRIMARY KEY (id_tipo_venta_pk)
);

CREATE SEQUENCE s_tipo_venta
INCREMENT BY 1
START WITH 1
MINVALUE 0;

create table entidad (
id_entidad_pk number not null,
nombre_entidad varchar(60),
CONSTRAINT c_id_entidad_pk PRIMARY KEY (id_entidad_pk)
);

CREATE SEQUENCE s_entidad
INCREMENT BY 1
START WITH 1
MINVALUE 0;

create table municipios (
id_municipio_pk number not null,
nombre_municipio varchar(60),
id_entidad_fk number,
CONSTRAINT c_id_municipio_pk PRIMARY KEY (id_municipio_pk),
CONSTRAINT c_id_entidad_fk FOREIGN KEY(id_entidad_fk) references entidad(id_entidad_pk)
);

CREATE SEQUENCE s_municipios
INCREMENT BY 1
START WITH 1
MINVALUE 0;

create table CODIGOS_POSTALES(
	ID_PK NUMBER,
	CODIGO_POSTAL VARCHAR(10),
	NOMBRE_COLONIA VARCHAR(200),
	id_municipio_fk NUMBER,
	CONSTRAINT ID_cp_pk PRIMARY KEY(ID_PK),
	CONSTRAINT c_id_munc_fk FOREIGN KEY(id_municipio_fk) references municipios(id_municipio_pk)
	);
CREATE SEQUENCE s_codigos_postales
INCREMENT BY 1
START WITH 1
MINVALUE 0;

CREATE SEQUENCE s_codigo_postal
INCREMENT BY 1
START WITH 1
MINVALUE 0;

create table usuario(
	id_usuario_pk number not null,
	nombre_usuario varchar(120) not null,
	aPaterno_usuario varchar(120) not null,
	aMaterno_usuario varchar(120),
	contrasena_usuario varchar(255),
	sexo_usuario char(1),
	telefono_movil_usuario number(15),
	telefono_fijo_usuario number (15),
	id_nextel_usuario varchar(15),
	correo_usuario varchar(255),
	numero_interior_usuario number(3),
	numero_exterio_usuario number(3),
	referencia_direcion_usuario varchar(255),
	calle_usuario varchar(255),
	colonia_usuario varchar (255),
	dias_credito_usuario number(3),
	rfc_usuario varchar (13),
	credito_limite_usuario number(10),
	clave_usuario varchar(10),
	sitio_web varchar(120),
	id_municipio_fk number,
	id_rol_fk number,
	latitud_usuario varchar(30),
	longitud_usuario varchar(30),
	CONSTRAINT c_id_usuario_pk PRIMARY KEY (id_usuario_pk),
	CONSTRAINT c_id_municipio_fk FOREIGN KEY(id_municipio_fk) references municipios(id_municipio_pk),
	CONSTRAINT c_id_rol_fk FOREIGN KEY(id_rol_fk) references rol(id_rol_pk)
);

CREATE SEQUENCE s_usuario
INCREMENT BY 1
START WITH 1
MINVALUE 0;


create table sucursal(
id_sucursal_pk number not null,
nombre_sucursal varchar(255),
calle_sucursal varchar(255),
colonia_sucursal varchar (255),
telefono_sucursal number(15),
id_usuario_sucursal_fk number,
id_municipio_fk number,
CONSTRAINT c_id_usuario_sucursal FOREIGN KEY(id_usuario_sucursal_fk) references usuario(id_usuario_pk),
CONSTRAINT c_id_municipio_sucursal_fk FOREIGN KEY(id_municipio_fk) references municipios(id_municipio_pk),
CONSTRAINT c_id_sucursal_pk PRIMARY KEY (id_sucursal_pk)
);

CREATE SEQUENCE s_sucursal
INCREMENT BY 1
START WITH 1
MINVALUE 0;

create table cuenta_bancaria(
id_cuenta_bancaria_pk number,
nombre_banco varchar(120),
id_usuario_fk number,
benificiario varchar(120),
clave_interbancaria varchar(60),
CONSTRAINT c_id_usuario_cuenta_fk FOREIGN KEY(id_usuario_fk) references usuario(id_usuario_pk),
CONSTRAINT c_id_cuenta_bancaria_pk PRIMARY KEY (id_cuenta_bancaria_pk)

);

CREATE SEQUENCE s_cuenta_bancaria
INCREMENT BY 1
START WITH 1
MINVALUE 0;

create table tipo_compra(
id_tipo_compra_pk number,
descripcion_tipo_compra varchar(255),
nombre_tipo_compra varchar(90),
CONSTRAINT c_id_tipo_compra_pk PRIMARY KEY (id_tipo_compra_pk)
);

CREATE SEQUENCE s_tipo_compra
INCREMENT BY 1
START WITH 1
MINVALUE 0;

create table camion(
id_camion_pk number,
descripcion varchar(255),
numero_factura number(15),
id_usuario_fk number,
id_tipo_compra_fk number,
CONSTRAINT c_id_tipo_compra_fk FOREIGN KEY(id_tipo_compra_fk) references tipo_compra(id_tipo_compra_pk),
CONSTRAINT c_id_usuario_camion_fk FOREIGN KEY(id_usuario_fk) references usuario(id_usuario_pk),
CONSTRAINT c_id_camion_pk PRIMARY KEY (id_camion_pk)
);

CREATE SEQUENCE s_camion
INCREMENT BY 1
START WITH 1
MINVALUE 0;

create table existencia_producto(
id_existencia_producto_pk number not null,
id_sucursal_fk number,
id_producto_fk varchar(8),
kilos_existencia number,
cantidad_empaque number,
id_tipo_empaque number,
kilos_empaque number,
id_camion_fk number,
CONSTRAINT c_id_camion_existencia_fk FOREIGN KEY(id_camion_fk) references camion(id_camion_pk),
CONSTRAINT c_id_tipo_empaque_fk FOREIGN KEY(id_tipo_empaque) references tipo_empaque(id_tipo_empaque_pk),
CONSTRAINT c_id_producto_existencia_fk FOREIGN KEY(id_producto_fk) references producto(id_producto_pk),
CONSTRAINT c_id_sucursal_existencia_fk FOREIGN KEY(id_sucursal_fk) references sucursal(id_sucursal_pk),
CONSTRAINT c_id_existencia_producto_pk PRIMARY KEY (id_existencia_producto_pk)
);


CREATE SEQUENCE s_existencia_producto
INCREMENT BY 1
START WITH 1
MINVALUE 0;


--###Esta Tabla aun no la ocupamos###-
create table compra_productos(
id_sucursal_fk number,
id_producto_fk number,
kilos_comprados number,
cantidad_empaque number,
id_tipo_empaque number,
kilos_empaque number,
precio_kilo number,
id_camion_fk number,
id_tipo_compra_fk number,
CONSTRAINT c_id_camion_compra_fk FOREIGN KEY(id_camion_fk) references camion(id_camion_pk),
CONSTRAINT c_id_tipo_empaque_compra_fk FOREIGN KEY(id_tipo_empaque) references tipo_empaque(id_tipo_empaque_pk),
CONSTRAINT c_id_producto_compra_fk FOREIGN KEY(id_producto_fk) references producto(id_producto_pk),
CONSTRAINT c_id_sucursal_compra_fk FOREIGN KEY(id_sucursal_fk) references sucursal(id_sucursal_pk),
CONSTRAINT c_id_tipo_compra_productos_fk FOREIGN KEY(id_tipo_compra_fk) references tipo_compra(id_tipo_compra_pk)
);

CREATE SEQUENCE s_compra_productos
INCREMENT BY 1
START WITH 1
MINVALUE 0;

--###Esta Tabla aun no la ocupamos###-

create table status_venta(
id_status_pk number not null,
nombre_status varchar (50) not null,
descripcion_status varchar(75),
CONSTRAINT c_id_status_fk PRIMARY KEY(id_status_pk)
);

CREATE SEQUENCE s_status_venta
INCREMENT BY 1
START WITH 1
MINVALUE 0;


INSERT INTO STATUS_VENTA (ID_STATUS_pk,NOMBRE_STATUS,DESCRIPCION_STATUS)VALUES(S_STATUS_VENTA.NextVal,'Vendido','Se hizo una venta aun no pagada')
INSERT INTO STATUS_VENTA (ID_STATUS_pk,NOMBRE_STATUS,DESCRIPCION_STATUS)VALUES(S_STATUS_VENTA.NextVal,'Pagado','Se ha realizado el pago en caja.')

create table venta(
id_venta_pk number not null,
id_cliente_fk number,
id_vendedor_fk number,
fecha_venta date,
fecha_promesa_pago date,
status_fk number,
CONSTRAINT c_id_cliente_fk FOREIGN KEY(id_cliente_fk) references usuario(id_usuario_pk),
CONSTRAINT c_id_vendedor_fk FOREIGN KEY(id_vendedor_fk) references usuario(id_usuario_pk),
CONSTRAINT c_id_venta_pk PRIMARY KEY (id_venta_pk),
CONSTRAINT c_status_fk FOREIGN KEY(STATUS_FK) references STATUS_VENTA(ID_STATUS_PK);
);

CREATE SEQUENCE s_venta
INCREMENT BY 1
START WITH 1
MINVALUE 0;

create table STATUS(
	ID_PK number,
	STATUS VARCHAR(20),
	DESCRIPCION_STATUS VARCHAR(80),
	CONSTRAINT c_id_cli_status_pk PRIMARY KEY(ID_PK) 
	);

CREATE SEQUENCE s_status
INCREMENT BY 1
START WITH 1
MINVALUE 0;

INSERT INTO STATUS (id_pk,status,descripcion_status) values (s_status.NextVal,'ACTIVO','EL CLIENTE SE ENCUENTRA ACTIVO'),
INSERT INTO STATUS (id_pk,status,descripcion_status) values (s_status.NextVal,'INACTIVO','EL CLIENTE SE ENCUENTRA INACTIVO'),

CREATE TABLE CLIENTE(
	ID_CLIENTE  NUMBER, 
	NOMBRE  VARCHAR(50), 
	APELLIDO_PATERNO  VARCHAR(50), 
	APELLIDO_MATERNO  VARCHAR(50), 
	EMPRESA  VARCHAR(20), 
	CALLE  VARCHAR(60), 
	SEXO  CHAR(1), 
	FECHA_NACIMIENTO  DATE, 
	TELEFONO_MOVIL  NUMBER, 
	TELEFONO_FIJO  NUMBER, 
	EXTENSION  NUMBER, 
	NUM_INT  NUMBER, 
	NUM_EXT  NUMBER, 
	CLAVECELULAR  NUMBER, 
	LADACELULAR  NUMBER, 
	ID_CP NUMBER, 
	CALLEFISCAL  VARCHAR(50), 
	NUMINTFIS  NUMBER, 
	NUMEXTFIS  NUMBER,  
	ID_CP_FISCAL NUMBER, 
	NEXTEL  VARCHAR(20), 
	RAZON  VARCHAR(100), 
	RFC  VARCHAR(20),  
	LADAOFICINA  VARCHAR(20), 
	CLAVEOFICINA  VARCHAR(20), 
	NEXTELCLAVE  VARCHAR(20),
	STATUS NUMBER,
	CONSTRAINT c_id_cliente_pk PRIMARY KEY (ID_CLIENTE),
	CONSTRAINT c_id_cod_fk FOREIGN KEY(ID_CP) references CODIGOS_POSTALES(ID_PK),
	CONSTRAINT c_id_cod__FIS_fk FOREIGN KEY(ID_CP_FISCAL) references CODIGOS_POSTALES(ID_PK),
	CONSTRAINT c_id_status_fk FOREIGN KEY(STATUS) references STATUS (ID_PK)
	);

CREATE SEQUENCE S_CLIENTE
INCREMENT BY 1
START WITH 1
MINVALUE 0;


create table venta_producto(
id_venta_producto_pk number not null,
id_subproducto_fk varchar(8),
precio_producto number(10,2),
kilos_vendidos number(15,3),
cantidad_empaque number(6),
total_venta number(15,4),
id_tipo_empaque_fk number,
id_tipo_venta_fk number,
id_venta_fk number NOT NULL ENABLE,
CONSTRAINT c_id_tipo_venta_fk FOREIGN KEY(id_tipo_venta_fk) references tipo_venta(id_tipo_venta_pk),
CONSTRAINT c_id_tipo_empaque_venta_fk FOREIGN KEY(id_tipo_empaque_fk) references tipo_empaque(id_tipo_empaque_pk),
CONSTRAINT c_id_producto_venta_fk FOREIGN KEY(id_subproducto_fk) references subproducto(ID_SUBPRODUCTO_PK),
CONSTRAINT c_id_venta_fk FOREIGN KEY(id_venta_fk) references VENTA(ID_VENTA_PK),
CONSTRAINT c_id_venta_producto_pk PRIMARY KEY (id_venta_producto_pk)
);

CREATE SEQUENCE s_venta_producto
INCREMENT BY 1
START WITH 1
MINVALUE 0;


create table mantenimiento_precio(
id_subproducto_fk varchar(8),
id_tipo_empaque_fk number,
precio_venta number(8,2),
precio_minimo number(8,2),
precio_maximo number(8,2),
CONSTRAINT c_precio_id_subproducto_fk FOREIGN KEY(id_subproducto_fk) references subproducto(id_subproducto_pk),
CONSTRAINT c_precio_id_empaque_fk FOREIGN KEY(id_tipo_empaque_fk) references tipo_empaque(id_tipo_empaque_pk)
);

CREATE SEQUENCE s_entidad1
INCREMENT BY 1
START WITH 1
MINVALUE 0;

create table correos(
	ID_PK number,
	id_cliente_fk number,
	correo varchar(100),
	tipo varchar(30),
	CONSTRAINT c_id_corr_pk PRIMARY KEY(ID_PK),
	CONSTRAINT C_ID_CLIENTE_COR_FK FOREIGN KEY (id_cliente_fk) references cliente(id_cliente)
);


//tablas para llevar registro de las entradas de ajos a la central

MINVALUE 0;
Create table entrada_producto_central(
id_entrada number(8),
precio_venta number(8,2),
toneladas number(12,2),
fecha  date,
CONSTRAINT c_id_entrada_pk PRIMARY KEY (id_entrada)
);

CREATE SEQUENCE S_ENTRADA_PRODUCTO_CENTRAL 
INCREMENT BY 1
START WITH 1
MINVALUE 0;
	

//tablas para llevar registro de las entradas de ajos a la central


--tablas eliminadas
--COMPRA_PRODUCTOS
--EXISTENCIA_PRODUCTO



