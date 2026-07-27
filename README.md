# StockFlow — Sistema de Gestión de Inventario

StockFlow es una aplicación web para la administración de productos, movimientos de inventario y alertas de existencias.

El proyecto está organizado como un **monorepositorio** compuesto por:

* Un backend desarrollado con **Spring Boot**.
* Un frontend desarrollado con **Angular**.
* Una base de datos **H2 en memoria**.

La aplicación permite registrar entradas y salidas de productos, consultar el historial de movimientos y generar alertas cuando las existencias alcanzan niveles mínimos o críticos.

---

## Funcionalidades principales

* Registro y consulta de productos.
* Registro de entradas y salidas de inventario.
* Validación de stock disponible.
* Consulta paginada del historial de movimientos.
* Generación automática de alertas de stock.
* Clasificación de alertas por severidad.
* Manejo global de excepciones.
* Validación de datos de entrada.
* Circuit Breaker, Retry y Rate Limiter con Resilience4j.
* Interfaz web desarrollada con Angular y PrimeNG.

---

## Estructura del proyecto

```text
stockflow/
├── inventory-service/
│   ├── src/
│   ├── pom.xml
│   ├── mvnw
│   └── mvnw.cmd
│
├── inventory-app/
│   ├── src/
│   ├── angular.json
│   ├── package.json
│   └── package-lock.json
│
├── .gitignore
└── README.md
```

---

## Tecnologías utilizadas

### Backend

* Java.
* Spring Boot 4.
* Spring Web.
* Spring Data JPA.
* Hibernate.
* Bean Validation.
* Maven.
* H2 Database.
* Resilience4j.
* Spring Boot Actuator.
* Spring AOP.
* HikariCP.

### Frontend

* Angular 19.
* TypeScript.
* HTML5.
* CSS3.
* PrimeNG 19.
* PrimeIcons.
* RxJS.
* Angular Router.
* Angular HttpClient.
* npm.
* Node.js.

---

## Requisitos de instalación

### Backend

* Java JDK 21.
* Maven 3.9 o superior, en caso de no utilizar Maven Wrapper.
* Git.

Verificar las versiones instaladas:

```bash
java --version
mvn --version
git --version
```

No es necesario instalar PostgreSQL, MySQL u otro servidor de base de datos, porque el proyecto utiliza **H2 en memoria**.

### Frontend

* Node.js 20 LTS.
* npm.
* Angular CLI 19.
* Git.

Verificar las versiones instaladas:

```bash
node --version
npm --version
ng version
```

Instalar Angular CLI 19:

```bash
npm install -g @angular/cli@19
```

---

## Clonar el proyecto

```bash
git clone https://github.com/EduarRugamas/stockflow.git
```

Ingresar al directorio principal:

```bash
cd stockflow
```

---

## Configuración del backend

La configuración principal se encuentra en:

```text
inventory-service/src/main/resources/application.yml
```

El proyecto utiliza una base de datos H2 en memoria con una configuración similar a la siguiente:

```yaml
spring:
  application:
    name: inventory-service

  datasource:
    url: jdbc:h2:mem:inventorydb
    driver-class-name: org.h2.Driver
    username: sa
    password:

  jpa:
    database-platform: org.hibernate.dialect.H2Dialect

    hibernate:
      ddl-auto: create-drop

    show-sql: true

  h2:
    console:
      enabled: true
      path: /h2-console
```

Los datos se almacenan únicamente mientras el backend está en ejecución. Cuando la aplicación se detiene, la información guardada en memoria se elimina.

La consola de H2 estará disponible en:

```text
http://localhost:8080/h2-console
```

Datos de conexión:

```text
JDBC URL: jdbc:h2:mem:inventorydb
Usuario: sa
Contraseña: dejar vacía
```

---

## Configuración del frontend

La URL del backend se configura en:

```text
inventory-app/src/environments/environment.ts
```

Ejemplo:

```typescript
export const environment = {
  production: false,
  apiUrl: 'http://localhost:8080/api/v1'
};
```

---

## Ejecutar el proyecto en desarrollo

El backend y el frontend deben ejecutarse en terminales separadas.

### Ejecutar el backend

Ingresar al directorio:

```bash
cd backend
```

Con Maven:

```bash
mvn spring-boot:run
```

Con Maven Wrapper en Linux o macOS:

```bash
./mvnw spring-boot:run
```

Con Maven Wrapper en Windows:

```powershell
mvnw.cmd spring-boot:run
```

El backend estará disponible en:

```text
http://localhost:8080
```

### Ejecutar el frontend

En otra terminal, ingresar al frontend:

```bash
cd frontend
```

Instalar las dependencias:

```bash
npm install
```

Ejecutar el servidor de desarrollo:

```bash
npm start
```

También puede ejecutarse con:

```bash
ng serve
```

El frontend estará disponible en:

```text
http://localhost:4200
```

---

## Ejecución rápida

### Terminal 1 — Backend

```bash
cd backend
./mvnw spring-boot:run
```

### Terminal 2 — Frontend

```bash
cd frontend
npm install
npm start
```

Abrir la aplicación en:

```text
http://localhost:4200
```

---

## Contacto

**Desarrollador:** Eduardo Rugamas
**Correo electrónico:** `juaneduardo021299@hotmail.com`
**GitHub:** `https://github.com/EduarRugamas`
**Repositorio:** `https://github.com/EduarRugamas/stockflow`

---

## Licencia

Este proyecto se distribuye bajo la licencia MIT.

```text
MIT License

Copyright (c) 2026 Eduar Rugamas

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files, to deal in the Software
without restriction, including without limitation the rights to use, copy,
modify, merge, publish, distribute, sublicense, and/or sell copies of the
Software.
```
