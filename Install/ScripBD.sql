--BASE DE DATOS : choni
--USUARIO: choni
--Contraseña: choni


create table rol(
id_rol_pk number(3) not null,
nombre_rol varchar(120) not null,
descripcion_rol varchar(255),
CONSTRAINT c_id_rol_pk PRIMARY KEY (id_rol_pk)
);

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
id_producto_fk varchar(4),
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
	razon_social_usuario varchar(100),
	fecha_alta_usuario Date,
	latitud_usuario varchar(30),
	longitud_usuario varchar(30),
	id_sucursal_fk number,
	CONSTRAINT c_id_usuario_pk PRIMARY KEY (id_usuario_pk),
	CONSTRAINT c_id_sucuclie_fk FOREIGN KEY(id_sucursal_fk) references sucursal(id_sucursal_pk),
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
CP_SUCURSAL number,
telefono_sucursal number(15),
num_int number,
num_ext number,
status_sucursal number,
CONSTRAINT c_id_codi_sucu_fk FOREIGN KEY(CP_SUCURSAL) references codigos_postales(id_pk),
CONSTRAINT c_id_status_sucu_fk FOREIGN KEY(status_sucursal) references status(id_pk),
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
PRECIO_MINIMO NUMBER(5,0), 
PRECIO_VENTA NUMBER(5,0), 
PRECIO_MAXIMO NUMBER(5,0), 
ESTATUS_BLOQUEO CHAR(1 BYTE),
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
fecha_pago date,
id_sucursal_fk number,
CONSTRAINT c_id_sucu_venta_fk FOREIGN KEY(id_sucursal_fk) references sucursal(id_sucursal_pk),
CONSTRAINT c_id_cliente_fk FOREIGN KEY(id_cliente_fk) references CLIENTE(id_cliente),
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
id_sucursal_fk number,
COSTOREAL number,
COSTOMERMA number,
CONSTRAINT c_sucursa_id_id_sucursal_fk FOREIGN KEY(id_sucursal_fk) references sucursal(id_sucursal_pk),
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

Create table ANALISIS_MERCADO(
id_entrada number(8),
ID_SUBPRODUCTO varchar(8),
precio_venta number(8,2),
toneladas number(12,2),
fecha  date,
REMANENTE number(12,2),
CONSTRAINT c_id_entrada_pk PRIMARY KEY (id_entrada),
CONSTRAINT C_ID_SUBPRODUCTO_epc_FK FOREIGN KEY (ID_SUBPRODUCTO) references SUBPRODUCTO(ID_SUBPRODUCTO_PK)
);

CREATE SEQUENCE S_ANALISIS_MERCADO 
INCREMENT BY 1
START WITH 1
MINVALUE 0;
	

//tablas para llevar registro de las entradas de ajos a la central


--tablas eliminadas
--COMPRA_PRODUCTOS
--EXISTENCIA_PRODUCTO



limpiar lista de seleccionados.
no agregar correos vacios.
no inicializar lista de municipios.
manejar integers en enteros de campos.




  CREATE TABLE  ENTRADAMERCANCIA (
    ID_EM_PK NUMBER, 
  	ID_PROVEDOR_FK NUMBER, 
  	MOVIMIENTO NUMBER, 
  	FECHA DATE, 
  	REMISION VARCHAR2(20 BYTE), 
  	ID_SUCURSAL_FK NUMBER, 
  	IDENTIFICADOR VARCHAR2(20 BYTE), 
  	ID_STATUS_FK NUMBER, 
  	KILOSTOTALES NUMBER, 
  	KILOSTOTALESPROVEDOR NUMBER, 
  	COMENTARIOS VARCHAR2(100 BYTE), 
  	FECHAREMISION DATE, 
  	TICKETPROVEDOR VARCHAR2(20 BYTE), 
  	TICKETBASCULA VARCHAR2(20 BYTE)
  );
  ALTER TABLE ENTRADAMERCANCIA ADD CONSTRAINT C_EM_ID_EM_PK PRIMARY KEY (ID_EM_PK)
  ALTER TABLE ENTRADAMERCANCIA ADD CONSTRAINT C_EM_ID_PROVEDOR_FK FOREIGN KEY (ID_PROVEDOR_FK)
REFERENCES PROVEDORES (ID_PROVEDOR_PK) ENABLE;
  ALTER TABLE ENTRADAMERCANCIA ADD CONSTRAINT C_EM_ID_SUCURSAL_FK FOREIGN KEY (ID_SUCURSAL_FK)
REFERENCES SUCURSAL (ID_SUCURSAL_PK) ENABLE;
  ALTER TABLE ENTRADAMERCANCIA ADD CONSTRAINT ENTRADAMERCANCIA_FK1 FOREIGN KEY (ID_STATUS_FK)
REFERENCES STATUS_ENTRADA_MERCANCIA (ID_SEM_PK) ENABLE;


CREATE SEQUENCE S_EntradaMercancia
INCREMENT BY 1
START WITH 1
MINVALUE 0;

CREATE TABLE ENTRADAMERCANCIAPRODUCTO 
   (  ID_EMP_PK NUMBER, 
  ID_EM_FK NUMBER, 
  ID_SUBPRODUCTO_FK VARCHAR2(8 BYTE), 
  ID_TIPO_EMPAQUE_FK NUMBER, 
  KILOS_TOTALES NUMBER, 
  CANTIDAD_EMPACAQUE NUMBER, 
  COMENTARIOS VARCHAR2(100 BYTE), 
  ID_BODEGA_FK NUMBER, 
  ID_TIPO_CONVENIO_FK NUMBER, 
  CONVENIO NUMBER, 
  KILOSPROMPROD NUMBER
   );

  ALTER TABLE ENTRADAMERCANCIAPRODUCTO ADD CONSTRAINT C_EMP_ID_EMP_PK PRIMARY KEY (ID_EMP_PK);
  ALTER TABLE ENTRADAMERCANCIAPRODUCTO ADD CONSTRAINT C_EMP_ID_EM_FK FOREIGN KEY (ID_EM_FK)
    REFERENCES ENTRADAMERCANCIA (ID_EM_PK) ENABLE;
  ALTER TABLE ENTRADAMERCANCIAPRODUCTO ADD CONSTRAINT C_EMP_ID_SUBPRODUCTO_FK FOREIGN KEY (ID_SUBPRODUCTO_FK)
    REFERENCES SUBPRODUCTO (ID_SUBPRODUCTO_PK) ENABLE;
  ALTER TABLE ENTRADAMERCANCIAPRODUCTO ADD CONSTRAINT C_EMP_ID_TIPO_EMPAQUE_FK FOREIGN KEY (ID_TIPO_EMPAQUE_FK)
    REFERENCES TIPO_EMPAQUE (ID_TIPO_EMPAQUE_PK) ENABLE;
  ALTER TABLE ENTRADAMERCANCIAPRODUCTO ADD CONSTRAINT ENTRADAMERCANCIAPRODUCTO_FK2 FOREIGN KEY (ID_BODEGA_FK)
    REFERENCES BODEGA (ID_BD_PK) ENABLE;

CREATE SEQUENCE S_EntradaMercanciaProducto
INCREMENT BY 1
START WITH 1
MINVALUE 0;


CREATE TABLE EXISTENCIA_PRODUCTO 
   (  ID_EXP_PK NUMBER, 
  ID_EM_FK NUMBER, 
  ID_SUBPRODUCTO_FK VARCHAR2(8 BYTE), 
  ID_TIPO_EMPAQUE_FK NUMBER, 
  KILOS_TOTALES NUMBER, 
  CANTIDAD_EMPACAQUE NUMBER, 
  COMENTARIOS VARCHAR2(100 BYTE), 
  ID_BODEGA_FK NUMBER, 
  ID_TIPO_CONVENIO_FK NUMBER, 
  CONVENIO NUMBER, 
  KILOSPROMPROD NUMBER, 
  PRECIO_MINIMO NUMBER(5,0), 
  PRECIO_VENTA NUMBER(5,0), 
  PRECIO_MAXIMO NUMBER(5,0), 
  ESTATUS_BLOQUEO CHAR(1 BYTE), 
  ID_SUCURSAL_FK NUMBER
   );--

  ALTER TABLE EXISTENCIA_PRODUCTO MODIFY (ID_SUCURSAL_FK NOT NULL ENABLE);
  ALTER TABLE EXISTENCIA_PRODUCTO ADD CONSTRAINT C_EP_ID_EMP_PK PRIMARY KEY (ID_EXP_PK);
  ALTER TABLE EXISTENCIA_PRODUCTO ADD CONSTRAINT C_EP_ID_BODEGA_FK FOREIGN KEY (ID_BODEGA_FK)
    REFERENCES BODEGA (ID_BD_PK) ENABLE;
  ALTER TABLE EXISTENCIA_PRODUCTO ADD CONSTRAINT C_EP_ID_EM_FK FOREIGN KEY (ID_EM_FK)
    REFERENCES ENTRADAMERCANCIA (ID_EM_PK) ENABLE;
  ALTER TABLE EXISTENCIA_PRODUCTO ADD CONSTRAINT C_EP_ID_SUBPRODUCTO_FK FOREIGN KEY (ID_SUBPRODUCTO_FK)
    REFERENCES SUBPRODUCTO (ID_SUBPRODUCTO_PK) ENABLE;
  ALTER TABLE EXISTENCIA_PRODUCTO ADD CONSTRAINT C_EP_ID_TIPO_CONVENIO_FK FOREIGN KEY (ID_TIPO_CONVENIO_FK)
    REFERENCES TIPO_CONVENIO (ID_TC_PK) ENABLE;
  ALTER TABLE EXISTENCIA_PRODUCTO ADD CONSTRAINT C_EP_ID_TIPO_EMPAQUE_FK FOREIGN KEY (ID_TIPO_EMPAQUE_FK)
    REFERENCES TIPO_EMPAQUE (ID_TIPO_EMPAQUE_PK) ENABLE;
  ALTER TABLE EXISTENCIA_PRODUCTO ADD CONSTRAINT EXISTENCIA_PRODUCTO_FK1 FOREIGN KEY (ID_SUCURSAL_FK)
    REFERENCES SUCURSAL (ID_SUCURSAL_PK) ENABLE;

CREATE TABLE PROVEDORES(
	ID_PROVEDOR_PK  NUMBER, 
	NOMBRE_PROVEDOR  VARCHAR(50), 
	A_PATERNO_PROVE  VARCHAR(50), 
	A_MATERNO_PROVE  VARCHAR(50), 
	EMPRESA  VARCHAR(20), 
	CALLE_PROVE  VARCHAR(60), 
	SEXO_PROVE  CHAR(1), 
	FECHA_NACIMIENTO_PROVE  DATE, 
	TELEFONO_MOVIL_PROVE  NUMBER, 
	TELEFONO_FIJO_PROVE  NUMBER, 
	EXTENSION_PROVE  NUMBER, 
	NUM_INT_PROVE  NUMBER, 
	NUM_EXT_PROVE  NUMBER, 
	CLAVECELULAR_PROVE  NUMBER, 
	LADACELULAR_PROVE  NUMBER, 
	ID_CP_PROVE_FK NUMBER, 
	CALLEFISCAL_PROVE  VARCHAR(50), 
	NUMINTFIS_PROVE  NUMBER, 
	NUMEXTFIS_PROVE  NUMBER,  
	ID_CP_FISCAL_PROVE_FK NUMBER, 
	NEXTEL_PROVE  VARCHAR(20), 
	RAZON_PROVE  VARCHAR(100), 
	RFC_PROVE  VARCHAR(20),  
	LADAOFICINA_PROVE  VARCHAR(20), 
	CLAVEOFICINA_PROVE  VARCHAR(20), 
	NEXTELCLAVE_PROVE  VARCHAR(20),
	ID_STATUS_FK NUMBER,
	CONSTRAINT c_id_provedor_pk PRIMARY KEY (ID_PROVEDOR_PK),
	CONSTRAINT c_id_cod_p_fk FOREIGN KEY(ID_CP_PROVE_FK) references CODIGOS_POSTALES(ID_PK),
	CONSTRAINT c_id_cod__FIS_p_fk FOREIGN KEY(ID_CP_FISCAL_PROVE_FK) references CODIGOS_POSTALES(ID_PK),
	CONSTRAINT c_id_status_p_fk FOREIGN KEY(ID_STATUS_FK) references STATUS (ID_PK)
	);

CREATE SEQUENCE S_PROVEDOR
INCREMENT BY 1
START WITH 1
MINVALUE 0;





create table STATUS_ENTRADA_MERCANCIA(
	ID_SEM_PK number,
	DESCRIPCION_STATUS VARCHAR(80),
	CONSTRAINT c_ID_SEM_PK_pk PRIMARY KEY(ID_SEM_PK) 
	);

CREATE SEQUENCE s_STATUS_ENTRADA_MERCANCIA
INCREMENT BY 1
START WITH 1
MINVALUE 0;


create table TIPO_ORDEN_COMPRA(
	ID_TOC_PK number,
	TIPO VARCHAR(30)
	DESCRIPCION_TIPO VARCHAR(80),
	CONSTRAINT c_ID_TOC_PK_pk PRIMARY KEY(ID_TOC_PK) 
	);

CREATE SEQUENCE s_TIPO_ORDEN_COMPRA
INCREMENT BY 1
START WITH 1
MINVALUE 0;

<---tabla para el control de roles y permisos-->
create table menu(
id_menu number(3) not null,
descripcion varchar(120) not null,
tipo number(1) not null,
nivel varchar(6) not null,
url_sistema varchar(120)not null,
CONSTRAINT c_id_menu PRIMARY KEY (id_menu)
);

CREATE SEQUENCE s_menu
INCREMENT BY 1
START WITH 1
MINVALUE 0;

create table acces_menu(
id_menu_fk number(3) not null,
ID_ROL_FK  NUMBER(3,0) NOT NULL,
CONSTRAINT c_acces_menu_ID_ROL_PK foreign KEY (ID_ROL_FK) REFERENCES ROL(ID_ROL_PK),
CONSTRAINT c_acces_menu_id_menu_fk foreign KEY (id_menu_fk) REFERENCES menu(id_menu)
);
drop table acces_menu;

CREATE SEQUENCE s_acces_menu
INCREMENT BY 1
START WITH 1
MINVALUE 0;

CREATE TABLE CATEGORIA_COSTOS(
ID_CATEGORIA_COSTOS NUMBER(3) NOT NULL,
DESCRIPCION VARCHAR(128),
CONSTRAINT C_CATEGORIA_COSTOS_PK PRIMARY KEY (ID_CATEGORIA_COSTOS) 
);

CREATE SEQUENCE s_CATEGORIA_COSTOS
INCREMENT BY 1
START WITH 1
MINVALUE 0;

<--Tabla tipo de costos-->
CREATE TABLE TIPO_COSTO (
ID_TIPO_COSTO NUMBER(3) NOT NULL,
ID_CATEGORIA_COSTO NUMBER(3) NOT NULL,
DESCRIPCION VARCHAR(128),
CONSTRAINT C_TIPO_COSTO_FK FOREIGN KEY (ID_CATEGORIA_COSTO) REFERENCES CATEGORIA_COSTOS(ID_CATEGORIA_COSTOS),
CONSTRAINT C_TIPO_COSTO_PK PRIMARY KEY (ID_TIPO_COSTO) 
);

CREATE SEQUENCE s_TIPO_COSTO
INCREMENT BY 1
START WITH 1
MINVALUE 0;

<--Inserts para categoria de costos-->
INSERT INTO CATEGORIA_COSTOS (ID_CATEGORIA_COSTOS, DESCRIPCION) VALUES(s_CATEGORIA_COSTOS.NextVal,'Sueldos y Salarios');
INSERT INTO CATEGORIA_COSTOS (ID_CATEGORIA_COSTOS, DESCRIPCION) VALUES(s_CATEGORIA_COSTOS.NextVal,'Mantenimiento de Equipo de Transporte');
INSERT INTO CATEGORIA_COSTOS (ID_CATEGORIA_COSTOS, DESCRIPCION) VALUES(s_CATEGORIA_COSTOS.NextVal,'Mantenimiento de Bodega');
INSERT INTO CATEGORIA_COSTOS (ID_CATEGORIA_COSTOS, DESCRIPCION) VALUES(s_CATEGORIA_COSTOS.NextVal,'Viáticos de Ruta');

<--fin roles y permisos-->

<---inserts para roles y permisos--->
INSERT INTO menu (id_menu,descripcion,tipo, nivel, url_sistema)  values (s_menu.nextVal, 'Catalogos', 1,'10','NULL');
INSERT INTO menu (id_menu,descripcion,tipo, nivel, url_sistema)  values (s_menu.nextVal, 'Categoria', 0,'10.1','/views/categoria.xhtml');
INSERT INTO menu (id_menu,descripcion,tipo, nivel, url_sistema)  values (s_menu.nextVal, 'Producto', 0,'10.2','/views/producto.xhtml');
INSERT INTO menu (id_menu,descripcion,tipo, nivel, url_sistema)  values (s_menu.nextVal, 'Usuario', 0,'10.3','/views/usuario.xhtml');
INSERT INTO menu (id_menu,descripcion,tipo, nivel, url_sistema)  values (s_menu.nextVal, 'Empaque', 0,'10.4','/views/empaque.xhtml');
INSERT INTO menu (id_menu,descripcion,tipo, nivel, url_sistema)  values (s_menu.nextVal, 'Sucursales', 0,'10.5','/views/sucursales.xhtml');
INSERT INTO menu (id_menu,descripcion,tipo, nivel, url_sistema)  values (s_menu.nextVal, 'Sucursales', 0,'10.6','/views/roles.xhtml');

INSERT INTO menu (id_menu,descripcion,tipo, nivel, url_sistema)  values (s_menu.nextVal, 'Productos', 1,'20','NULL');
INSERT INTO menu (id_menu,descripcion,tipo, nivel, url_sistema)  values (s_menu.nextVal, 'Mantenimiento Precios Menudeo', 0,'20.1','/views/mantenimientoPrecios.xhtml');
INSERT INTO menu (id_menu,descripcion,tipo, nivel, url_sistema)  values (s_menu.nextVal, 'Mantenimiento Precios Mayoreo', 0,'20.2','/views/mantenimientoMayoreo.xhtml');

INSERT INTO menu (id_menu,descripcion,tipo, nivel, url_sistema)  values (s_menu.nextVal, 'Ventas', 1,'30','NULL');
INSERT INTO menu (id_menu,descripcion,tipo, nivel, url_sistema)  values (s_menu.nextVal, 'Realizar Pedido', 0,'30.1','/views/venta.xhtml');
INSERT INTO menu (id_menu,descripcion,tipo, nivel, url_sistema)  values (s_menu.nextVal, 'Pagar Pedido', 0,'30.2','/views/buscaVenta.xhtml');
INSERT INTO menu (id_menu,descripcion,tipo, nivel, url_sistema)  values (s_menu.nextVal, 'Relación de Operaciónes', 0,'30.3','/views/relacionOperaciones.xhtml');
INSERT INTO menu (id_menu,descripcion,tipo, nivel, url_sistema)  values (s_menu.nextVal, 'Ventas al Mayoreo', 0,'30.4','/views/ventasMayoreo.xhtml');
INSERT INTO menu (id_menu,descripcion,tipo, nivel, url_sistema)  values (s_menu.nextVal, 'Ajuste de Existencias', 0,'30.3','/views/ajustesExistenciasMenudeo.xhtml');
INSERT INTO menu (id_menu,descripcion,tipo, nivel, url_sistema)  values (s_menu.nextVal, 'Relacion Entrada Salida', 0,'30.4','/views/relacionEntSalExAj.xhtml');
INSERT INTO menu (id_menu,descripcion,tipo, nivel, url_sistema)  values (s_menu.nextVal, 'Historial Entrada Menudeo', 0,'30.2.2','/views/historialEntradaMenudeo.xhtml');

INSERT INTO menu (id_menu,descripcion,tipo, nivel, url_sistema)  values (s_menu.nextVal, 'Clientes', 1,'40','NULL');
INSERT INTO menu (id_menu,descripcion,tipo, nivel, url_sistema)  values (s_menu.nextVal, 'Cliente', 0,'40.1','/views/clientes.xhtml');

INSERT INTO menu (id_menu,descripcion,tipo, nivel, url_sistema)  values (s_menu.nextVal, 'Provedores', 1,'50','NULL');
INSERT INTO menu (id_menu,descripcion,tipo, nivel, url_sistema)  values (s_menu.nextVal, 'Provedor', 0,'50.1','/views/provedor.xhtml');

INSERT INTO menu (id_menu,descripcion,tipo, nivel, url_sistema)  values (s_menu.nextVal, 'Análisis', 1,'60','NULL');
INSERT INTO menu (id_menu,descripcion,tipo, nivel, url_sistema)  values (s_menu.nextVal, 'Análisis de Mercado', 0,'60.1','/views/analisisMercado.xhtml');
INSERT INTO menu (id_menu,descripcion,tipo, nivel, url_sistema)  values (s_menu.nextVal, 'Top de Ventas', 0,'60.2','/views/topVentas.xhtml');
INSERT INTO menu (id_menu,descripcion,tipo, nivel, url_sistema)  values (s_menu.nextVal, 'Registro Entrada', 0,'60.4','/views/registroEntradaSalida.xhtml');
INSERT INTO menu (id_menu,descripcion,tipo, nivel, url_sistema)  values (s_menu.nextVal, 'Registros', 0,'60.5','/views/relacionHorarios.xhtml');

INSERT INTO menu (id_menu,descripcion,tipo, nivel, url_sistema)  values (s_menu.nextVal, 'Bodegas', 0,'10.6','/views/bodega.xhtml');

INSERT INTO menu (id_menu,descripcion,tipo, nivel, url_sistema)  values (s_menu.nextVal, 'Mercancia', 1,'70','NULL');
INSERT INTO menu (id_menu,descripcion,tipo, nivel, url_sistema)  values (s_menu.nextVal, 'Entrada de Mercancia', 0,'70.1','/views/entradaMercancia.xhtml');
INSERT INTO menu (id_menu,descripcion,tipo, nivel, url_sistema)  values (s_menu.nextVal, 'Transferencia de Mercancia', 0,'70.2','/views/transferenciaMercancia.xhtml');
INSERT INTO menu (id_menu,descripcion,tipo, nivel, url_sistema)  values (s_menu.nextVal, 'Relación de Operaciones', 0,'70.3','/views/relOperEntraMercancia.xhtml');
INSERT INTO menu (id_menu,descripcion,tipo, nivel, url_sistema)  values (s_menu.nextVal, 'Existencias', 0,'70.4','/views/existencias.xhtml');

INSERT INTO menu (id_menu,descripcion,tipo, nivel, url_sistema)  values (s_menu.nextVal, 'Permisos', 1,'80','NULL');
INSERT INTO menu (id_menu,descripcion,tipo, nivel, url_sistema)  values (s_menu.nextVal, 'Asignar permiso a Rol', 0,'80.1','/views/permisoRol.xhtml');

INSERT INTO menu (id_menu,descripcion,tipo, nivel, url_sistema)  values (s_menu.nextVal, 'Mayoreo', 1,'90','NULL');
INSERT INTO menu (id_menu,descripcion,tipo, nivel, url_sistema)  values (s_menu.nextVal, 'Realizar Pedido', 0,'90.1','/views/ventasMayoreo.xhtml');
INSERT INTO menu (id_menu,descripcion,tipo, nivel, url_sistema)  values (s_menu.nextVal, 'Relación de Operaciones', 0,'90.2','/views/relacionOperacionesMayoreo.xhtml');
INSERT INTO menu (id_menu,descripcion,tipo, nivel, url_sistema)  values (s_menu.nextVal, 'Pagar Pedido', 0,'90.3','/views/buscaVentaMayoreo.xhtml');

INSERT INTO acces_menu (id_menu_fk, ID_ROL_FK) values(1,1);
INSERT INTO acces_menu (id_menu_fk, ID_ROL_FK) values(2,1);
INSERT INTO acces_menu (id_menu_fk, ID_ROL_FK) values(3,1);
INSERT INTO acces_menu (id_menu_fk, ID_ROL_FK) values(4,1);
INSERT INTO acces_menu (id_menu_fk, ID_ROL_FK) values(5,1); 
INSERT INTO acces_menu (id_menu_fk, ID_ROL_FK) values(6,1);

INSERT INTO acces_menu (id_menu_fk, ID_ROL_FK) values(7,1);
INSERT INTO acces_menu (id_menu_fk, ID_ROL_FK) values(8,1);

INSERT INTO acces_menu (id_menu_fk, ID_ROL_FK) values(9,1);
INSERT INTO acces_menu (id_menu_fk, ID_ROL_FK) values(10,1);
INSERT INTO acces_menu (id_menu_fk, ID_ROL_FK) values(11,1);
INSERT INTO acces_menu (id_menu_fk, ID_ROL_FK) values(12,1);

INSERT INTO acces_menu (id_menu_fk, ID_ROL_FK) values(13,1);
INSERT INTO acces_menu (id_menu_fk, ID_ROL_FK) values(14,1);
INSERT INTO acces_menu (id_menu_fk, ID_ROL_FK) values(15,1);
INSERT INTO acces_menu (id_menu_fk, ID_ROL_FK) values(16,1);

INSERT INTO acces_menu (id_menu_fk, ID_ROL_FK) values(17,1);
INSERT INTO acces_menu (id_menu_fk, ID_ROL_FK) values(18,1);

INSERT INTO acces_menu (id_menu_fk, ID_ROL_FK) values(19,1);

INSERT INTO acces_menu (id_menu_fk, ID_ROL_FK) values(20,1);
INSERT INTO acces_menu (id_menu_fk, ID_ROL_FK) values(21,1);
INSERT INTO acces_menu (id_menu_fk, ID_ROL_FK) values(22,1);
INSERT INTO acces_menu (id_menu_fk, ID_ROL_FK) values(23,1);
INSERT INTO acces_menu (id_menu_fk, ID_ROL_FK) values(24,1);
INSERT INTO acces_menu (id_menu_fk, ID_ROL_FK) values(25,1);
INSERT INTO acces_menu (id_menu_fk, ID_ROL_FK) values(26,1);
INSERT INTO acces_menu (id_menu_fk, ID_ROL_FK) values(27,1);
INSERT INTO acces_menu (id_menu_fk, ID_ROL_FK) values(28,1);
<--- fin inserts para roles y permisos--->

--Tablas de Ventas Mayoreo.

create table venta_Mayoreo(
id_venta_mayoreo_pk number not null,
id_cliente_fk number,
id_vendedor_fk number,
fecha_venta date,
fecha_promesa_pago date,
id_status_fk number,
fecha_pago date,
id_sucursal_fk number,
total_venta number,
id_tipo_venta_fk number,

CONSTRAINT c_vm_id_venta_mayoreo_pk PRIMARY KEY (id_venta_mayoreo_pk),
CONSTRAINT c_vm_id_sucu_venta_fk FOREIGN KEY(id_sucursal_fk) references sucursal(id_sucursal_pk),
CONSTRAINT c_vm_id_cliente_fk FOREIGN KEY(id_cliente_fk) references CLIENTE(id_cliente),
CONSTRAINT c_vm_id_vendedor_fk FOREIGN KEY(id_vendedor_fk) references usuario(id_usuario_pk),
CONSTRAINT c_vm_id_status_fk FOREIGN KEY(id_status_fk) references STATUS_VENTA(ID_STATUS_PK),
CONSTRAINT c_vmp_id_tipo_venta_fk FOREIGN KEY(id_tipo_venta_fk) references tipo_venta(id_tipo_venta_pk)
);


CREATE SEQUENCE s_venta_Mayoreo
INCREMENT BY 1
START WITH 1
MINVALUE 0;



create table ventaMayoreoProducto(
id_v_m_p_pk number not null,
id_venta_mayoreo_fk number NOT NULL ENABLE,
id_subproducto_fk varchar(8),
precio_producto number(10,2),
kilos_vendidos number(15,3),
cantidad_empaque number(6),
total_venta number(15,4),
id_tipo_empaque_fk number,
id_entrada_mercancia_fk number,

CONSTRAINT c_vmp_id_v_m_p_pk PRIMARY KEY (id_v_m_p_pk),
CONSTRAINT c_vmp_id_venta_mayoreo_fk FOREIGN KEY(id_venta_mayoreo_fk) references venta_Mayoreo(id_venta_mayoreo_pk),
CONSTRAINT c_vmp_id_subproducto_fk FOREIGN KEY(id_subproducto_fk) references subproducto(ID_SUBPRODUCTO_PK),
CONSTRAINT c_vmp_id_tipo_empaque_venta_fk FOREIGN KEY(id_tipo_empaque_fk) references tipo_empaque(id_tipo_empaque_pk),
CONSTRAINT c_vmp_id_entrada_mercancia_fk FOREIGN KEY(id_entrada_mercancia_fk) references ENTRADAMERCANCIA(ID_EM_PK)
);


CREATE SEQUENCE venta_Mayoreo_Producto
INCREMENT BY 1
START WITH 1
MINVALUE 0;


--Datos de Impresora:
--Vertical: escala a 78%


create table RegistroEntrada(
id_regEnt_pk number not null,
fechaEntrada DATE,
fechaSalida DATE,
latitudEntrada number,
longitudEntrada number,
latitudSalida number,
tatitudSalida number,
id_usuario_fk number,
id_sucursal_fk number,
CONSTRAINT c_re_id_regEnt_pk PRIMARY KEY (id_regEnt_pk),
CONSTRAINT c_re_id_usuario_fk FOREIGN KEY(id_usuario_fk) references usuario(ID_USUARIO_PK),
CONSTRAINT c_re_id_sucursal_fk FOREIGN KEY(id_sucursal_fk) references sucursal(ID_SUCURSAL_PK)
);

CREATE SEQUENCE S_RegistroEntrada
INCREMENT BY 1
START WITH 1
MINVALUE 0;


create table ExistenciaMenudeo(
    ID_EXMEN_PK NUMBER, 
    ID_SUBPRODUCTO_FK VARCHAR2(8 BYTE), 
    ID_SUCURSAL_FK NUMBER, 
    KILOS NUMBER, 
    CANTIDADEMPAQUE NUMBER, 
    IDTIPOEMPAQUEFK NUMBER, 
    IDSTATUSFK NUMBER,
CONSTRAINT c_em_id_exmen_pk PRIMARY KEY (id_exmen_pk),
CONSTRAINT C_1EM_ID_SUBPRODUCTO_FK FOREIGN KEY ("ID_SUBPRODUCTO_FK") REFERENCES SUBPRODUCTO (ID_SUBPRODUCTO_PK),
CONSTRAINT c_em_id_sucursal_fk FOREIGN KEY(id_sucursal_fk) references sucursal(ID_SUCURSAL_PK),
CONSTRAINT C_1EM_ID_IDTIPOEMPAQUEFK FOREIGN KEY (IDTIPOEMPAQUEFK) REFERENCES TIPO_EMPAQUE(ID_TIPO_EMPAQUE_PK),
CONSTRAINT C_1EM_ID_IDSTATUSFK FOREIGN KEY (IDSTATUSFK) STATUS_ENTRADA_MERCANCIA (ID_SEM_PK)
);

CREATE SEQUENCE S_ExistenciaMenudeo
INCREMENT BY 1
START WITH 1
MINVALUE 0;



  CREATE TABLE  ENTRADAMERCANCIAMENUDEO (
    ID_EMM_PK NUMBER, 
    ID_PROVEDOR_FK NUMBER, 
    FECHA DATE, 
    ID_SUCURSAL_FK NUMBER,  
    ID_STATUS_FK NUMBER, 
    KILOSTOTALES NUMBER, 
    KILOSTOTALESPROVEDOR NUMBER, 
    COMENTARIOS VARCHAR2(100 BYTE), 
    TICKETPROVEDOR VARCHAR2(20 BYTE), 
    TICKETBASCULA VARCHAR2(20 BYTE),
    FOLIO NUMBER,
    ID_USER_FK NUMBER,
    CONSTRAINT c_EMM_ID_EMM_PK PRIMARY KEY (ID_EMM_PK),
    CONSTRAINT c_EMM_ID_PROVEDOR_FK FOREIGN KEY(ID_PROVEDOR_FK) references PROVEDORES(ID_PROVEDOR_PK),
    CONSTRAINT c_EMM_ID_SUCURSAL_FK FOREIGN KEY(id_sucursal_fk) references sucursal(ID_SUCURSAL_PK),
    CONSTRAINT c_EMM_ID_USER_FK FOREIGN KEY(ID_USER_FK) references USUARIO(ID_USUARIO_PK),    
    CONSTRAINT c_EMM_ID_STATUS_FK FOREIGN KEY(ID_STATUS_FK) references STATUS_ENTRADA_MERCANCIA(ID_SEM_PK));

CREATE SEQUENCE S_ENTRADAMENUDEO
INCREMENT BY 1
START WITH 1
MINVALUE 0;


CREATE TABLE ENTRADAMENUDEOPRODUCTO (  
    ID_EMMP_PK NUMBER, 
    ID_EMM_FK NUMBER, 
    ID_SUBPRODUCTO_FK VARCHAR2(8 BYTE), 
    ID_TIPO_EMPAQUE_FK NUMBER, 
    KILOS_TOTALES NUMBER, 
    CANTIDAD_EMPACAQUE NUMBER, 
    COMENTARIOS VARCHAR2(100 BYTE),
    PRECIO NUMBER, 
    PORCENTAJEMERMA NUMBER, 
    CONSTRAINT C_EMMP_ID_EMMP_PK PRIMARY KEY (ID_EMMP_PK), 
    CONSTRAINT C_EMMP_ID_EMM_FK FOREIGN KEY(ID_EMM_FK) references ENTRADAMERCANCIAMENUDEO(ID_EMM_PK),
    CONSTRAINT C_EMMP_ID_SUBPRODUCTO_FK FOREIGN KEY(ID_SUBPRODUCTO_FK) references SUBPRODUCTO(ID_SUBPRODUCTO_PK),
    CONSTRAINT C_EMMP_ID_TIPO_EMPAQUE_FK FOREIGN KEY(ID_TIPO_EMPAQUE_FK) references TIPO_EMPAQUE(ID_TIPO_EMPAQUE_PK)
   );
CREATE SEQUENCE S_ENTRADAMENUDEOPRODUCTO
INCREMENT BY 1
START WITH 1
MINVALUE 0;


CREATE TABLE AJUSTE_EXISTENCIA_MENUDEO(
ID_AJUSTE_MENUDEO NUMBER NOT NULL,
ID_EXISTENCIA_MENUDEO_FK NUMBER,
ID_USUARIO_AJUSTE_FK NUMBER,
ID_USUARIO_APRUEBA_FK NUMBER,
ID_SUSCURSAL_FK NUMBER,
id_tipo_empaque_fk NUMBER,
FECHA_AJUSTE DATE,
EMPAQUE_ANTERIOR NUMBER(13,4),
EMPAQUE_AJUSTADOS NUMBER(13,4),
KILOS_ANTERIOR NUMBER(13,6),
KILOS_AJUSTADOS NUMBER (13,6),
OBSERVACIONES VARCHAR(255),
MOTIVO_AJUSTE CHAR(1),

CONSTRAINT c_ajuste_menudeo_pk PRIMARY KEY (ID_AJUSTE_MENUDEO),
CONSTRAINT c_AEM_EXISTENCIA_MENUDEO_fk FOREIGN KEY(ID_EXISTENCIA_MENUDEO_FK) references EXISTENCIAMENUDEO(ID_EXMEN_PK),
CONSTRAINT c_AEM_USUARIO_AJUSTE_fk FOREIGN KEY(ID_USUARIO_AJUSTE_FK) references usuario(id_usuario_pk),
CONSTRAINT c_AEM_USUARIO_APRUEBA_fk FOREIGN KEY(ID_USUARIO_APRUEBA_FK) references usuario(id_usuario_pk),
CONSTRAINT c_AEM_EMPAQUE_fk FOREIGN KEY(id_tipo_empaque_fk) references tipo_empaque(id_tipo_empaque_pk),
CONSTRAINT c_AEM_SUCURSAL_fk FOREIGN KEY(ID_SUSCURSAL_FK) references sucursal(id_sucursal_pk)
);

CREATE SEQUENCE s_AJUSTE_EXISTENCIA_MENUDEO
INCREMENT BY 1
START WITH 1
MINVALUE 0;

