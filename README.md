# 🚀 StockSync: Sistema de Gestión de Inventario (SaaS Multi-Inquilino)

## 🎯 Visión General del Proyecto

**StockSync** es un software como servicio (SaaS) diseñado para pequeñas y medianas empresas (PyMEs) con el objetivo de reemplazar los procesos manuales de gestión de inventario y pedidos. Este proyecto sirve como **portafolio técnico de nivel empresarial** para demostrar dominio en el diseño de arquitecturas distribuidas y resilientes.

El núcleo del desafío técnico es resolver la **concurrencia de inventario** y asegurar el **aislamiento estricto de datos (Multi-Tenancy)**.

-----

## 🏗️ Arquitectura y Metodología de Diseño

Este proyecto no es solo un CRUD; es una demostración de arquitectura moderna y procesos profesionales.

### 1\. Arquitectura Evolutiva (Monolito Modular)

El *backend* comienza como un **Monolito Modular** para agilizar el desarrollo inicial. Está diseñado siguiendo el patrón **Arquitectura Hexagonal (Puertos y Adaptadores)** para facilitar una futura migración a microservicios.

  * **Principio Clave:** El **Dominio** (lógica de negocio) es puro Java, sin dependencias de *frameworks* (JPA, Spring). La **Infraestructura** (controladores, adaptadores) se encarga de la conexión con el mundo exterior.
  * **Aislamiento de Código:** La estructura está organizada en **Contextos Delimitados (Bounded Contexts)** (ej. `tenantmanagement`, `productcatalog`) para que cada módulo sea autónomo y pueda ser extraído como microservicio con un simple "cortar y pegar".

### 2\. Metodología de Desarrollo

El desarrollo sigue el patrón de **Cortes Verticales (Vertical Slices)** e **Iteración Constante**, priorizando la funcionalidad de negocio de principio a fin. El *workflow* utiliza **TDD (Desarrollo Guiado por Pruebas)** para el diseño de la lógica de dominio.

-----

## 🛠️ Stack Tecnológico (The Tech Stack)

| Categoría | Herramienta | Propósito en el Proyecto |
| :--- | :--- | :--- |
| **Backend Core** | **Java 21**, **Spring Boot 3+** | Framework principal y base del monolito. |
| **Seguridad** | **JWT (RSA)**, **Spring Security** | Autenticación Asimétrica (RSA) y Autorización (RBAC) con `tenant_id` y `roles` en los *claims* del token. |
| **Capa de Datos** | **PostgreSQL** | Base de datos relacional principal. |
| **Gestión de Sesión** | **Redis** | Almacenamiento de **Refresh Tokens** para la gestión de sesiones y revocación (Logout). |
| **Asincronía (Futuro)** | **Apache Kafka** | Bus de mensajes para el desacoplamiento de servicios (ej. confirmación de órdenes, notificaciones) en la fase de microservicios. |
| **Migraciones DB** | **Liquibase** | Control de versiones y ejecución de scripts SQL, con aislamiento de datos de prueba mediante **Contextos**. |
| **Pruebas** | **Testcontainers** | Entornos de pruebas de integración efímeros (Postgres y Redis reales en Docker) para garantizar la funcionalidad de JPA y Redis. |

-----

## 🔑 Módulos Principales (Contextos Delimitados)

| Módulo | Descripción | Tarea Crítica del Portafolio |
| :--- | :--- | :--- |
| **Tenant Management** | Gestión de Inquilinos, Usuarios, Roles y Autenticación. | **Aislamiento Multi-Tenancy.** Creación del JWT con el `tenant_id` para filtrar todos los datos. |
| **Inventory Management** | Control de Stock, Costeo (WAC) y Movimientos. | **Alta Concurrencia.** Uso de Redis para el bloqueo atómico (`DECRBY`) del stock disponible durante las ventas. |
| **Order Management** | Creación y gestión del flujo de Órdenes de Compra (PO) y Venta (SO). | **Resiliencia Asíncrona.** Uso de Kafka para el procesamiento de eventos de confirmación de órdenes. |

