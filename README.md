# Pro-Emsalud

Sistema de gestión integral para laboratorio clínico desarrollado como una aplicación full-stack con **Angular**, **Spring Boot** y **MySQL**.

Pro-Emsalud fue diseñado para mejorar y digitalizar el flujo de trabajo de un laboratorio clínico, reduciendo tareas manuales, dependencia de documentos Excel y costos asociados a la impresión y gestión física de información.

> **Proyecto real:** desarrollado para apoyar las operaciones de un laboratorio clínico/pequeña empresa en Guatemala.

---

## 📋 Descripción

Pro-Emsalud centraliza la información operativa del laboratorio en una única aplicación, permitiendo gestionar pacientes, pruebas, resultados, servicios y ventas desde una interfaz web.

El sistema busca reemplazar procesos manuales y dispersos por un flujo de trabajo digital, estructurado y basado en el modelo de dominio del negocio.

Entre los principales objetivos del proyecto se encuentran:

- Digitalizar el almacenamiento y consulta de información.
- Reducir el uso de documentos Excel para registrar y consultar datos.
- Disminuir considerablemente los costos asociados a impresión.
- Centralizar el historial de los pacientes.
- Mantener la integridad y consistencia de los datos.
- Facilitar la búsqueda y consulta de información.
- Generar reportes digitales.
- Proporcionar información estratégica para la toma de decisiones.
- Preparar el sistema para funcionar localmente en entornos con conectividad limitada.

---

## 🎯 Impacto del proyecto

Uno de los objetivos principales de Pro-Emsalud fue reducir la dependencia de procesos físicos y documentos independientes.

### Resultados principales

- Reducción aproximada del **85%** en costos de impresión.
- Eliminación progresiva del uso de archivos Excel como mecanismo principal de almacenamiento y consulta.
- Centralización de la información de pacientes y resultados.
- Acceso más rápido al historial de los pacientes.
- Reducción de tareas manuales relacionadas con búsqueda y organización de información.
- Disponibilidad de métricas y visualizaciones para analizar el comportamiento del negocio.

El proyecto está orientado a resolver necesidades reales de operación y no únicamente a servir como demostración técnica.

---

## 🏗️ Arquitectura

La aplicación utiliza una arquitectura full-stack separando frontend, backend y persistencia.

```text
┌──────────────────────────────┐
│          Frontend            │
│          Angular             │
│                              │
│  Dashboard                   │
│  Pacientes                   │
│  Pruebas                     │
│  Resultados                  │
│  Servicios                   │
│  Consultas                   │
│  Reportes                    │
└──────────────┬───────────────┘
               │
               │ HTTP / REST
               ▼
┌──────────────────────────────┐
│           Backend            │
│         Spring Boot          │
│                              │
│  Controllers                 │
│  Services                    │
│  Repositories                │
│  DTOs / Projections          │
│  Validaciones                │
│  Manejo de errores           │
│  Reglas de negocio           │
└──────────────┬───────────────┘
               │
               │ JPA / Hibernate
               ▼
┌──────────────────────────────┐
│           MySQL              │
│                              │
│  Persistencia                │
│  Integridad de datos         │
│  Relaciones                  │
│  Historial                   │
└──────────────────────────────┘
```

---

## 🛠️ Tecnologías

### Frontend

- Angular
- TypeScript
- HTML5
- CSS3
- Bootstrap
- Bootstrap Icons
- ng2-charts
- Chart.js
- RxJS

### Backend

- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- REST API
- Bean Validation
- JPQL
- Projections
- Pageable

### Base de datos

- MySQL

### Generación de documentos

- PDF
- jsPDF
- html2canvas

### Integraciones

- WhatsApp Desktop

---

## ✨ Funcionalidades principales

### 👤 Gestión de pacientes

El sistema permite administrar la información de los pacientes y mantener un historial asociado a las operaciones realizadas dentro del laboratorio.

Entre las funcionalidades se incluyen:

- Registro de pacientes.
- Consulta de pacientes.
- Búsqueda por identificadores.
- Actualización de información.
- Historial asociado al paciente.
- Manejo de estados.
- Activación y desactivación de registros.

### 🧪 Gestión de pruebas y resultados

Permite administrar las pruebas realizadas por el laboratorio y almacenar los resultados correspondientes.

El sistema mantiene las relaciones entre:

```text
Paciente
   │
   └── Ordenes
          │
          └── Resultados
```

Esto permite consultar el historial de resultados de un paciente y mantener la información organizada de acuerdo con el modelo de dominio.

---

## 🗃️ Persistencia e integridad de datos

La aplicación utiliza Spring Data JPA e Hibernate para manejar la persistencia.

Se implementan:

- Relaciones entre entidades.
- Restricciones de integridad.
- Consultas mediante JPQL.
- Projections para consultas específicas.
- Consultas agrupadas.
- Paginación mediante Pageable.
- Manejo de estados.
- Validaciones.
- Soft delete.
- Activación y desactivación de registros.

El objetivo es evitar la eliminación innecesaria de información y mantener la consistencia histórica de los datos.

---

## ♻️ Soft Delete

Aunque el sistema maneja delete pero de manera segura, el sistema utiliza mecanismos de soft delete para determinados registros.

En lugar de eliminar físicamente la información de la base de datos, los registros pueden pasar a un estado inactivo.

Esto permite:

- Conservar información histórica.
- Evitar pérdida accidental de datos.
- Mantener relaciones existentes.
- Recuperar registros cuando sea necesario.
- Separar registros activos de registros inactivos.

---

## 🔎 Búsquedas y filtros

Las entidades principales cuentan con mecanismos de consulta y filtrado.

Entre ellos:

- Búsqueda por clave primaria.
- Consultas específicas por entidad.
- Filtros.
- Paginación.
- Ordenamiento.
- Consultas agrupadas.
- Consultas para métricas y reportes.

Esto permite evitar la necesidad de recorrer manualmente grandes cantidades de información.

---

## ⚠️ Validaciones y manejo de errores

La aplicación implementa manejo estructurado de errores tanto en frontend como en backend.

Las peticiones HTTP pueden devolver información detallada sobre:

- Errores de validación.
- Recursos no encontrados.
- Datos inválidos.
- Conflictos.
- Errores de persistencia.
- Errores internos del servidor.

El objetivo es proporcionar mensajes claros al usuario y facilitar el diagnóstico durante el mantenimiento de la aplicación.

---

## 📄 Generación de reportes

Pro-Emsalud permite generar reportes en formato PDF.

Esto facilita:

- Entrega de resultados.
- Almacenamiento digital.
- Impresión cuando sea necesaria.
- Compartir información con pacientes.

La generación de documentos se realiza desde el frontend utilizando herramientas de generación y captura de contenido.

---

## 💬 Integración con WhatsApp

El sistema incorpora integración con WhatsApp Desktop para facilitar el envío de información a los pacientes.

El flujo permite utilizar la información generada por el sistema y facilitar la comunicación con el paciente sin tener que copiar manualmente los datos entre diferentes aplicaciones.

---

## 📊 Dashboard y métricas

Pro-Emsalud incluye un dashboard orientado al análisis operativo y comercial del laboratorio.

Actualmente se pueden visualizar métricas relacionadas con:

- Ventas totales.
- Ventas actuales.
- Cantidad de resultados.
- Cantidad de pacientes.
- Pruebas más solicitadas.
- Ventas por período.
- Resultados por período.

También se utilizan gráficos para visualizar tendencias.

### Ejemplo de flujo de información

```text
TestResult
    │
    ├── Test
    │
    ├── Unit Price
    │
    └── Created At
          │
          ▼
      Agrupación
          │
          ▼
       Métricas
          │
          ▼
       Dashboard
```

Estas métricas permiten obtener información que puede utilizarse para tomar decisiones futuras sobre el estado y comportamiento del negocio.

---

## 📈 Consultas estratégicas

El backend utiliza consultas específicas para obtener información agregada sin necesidad de cargar todos los registros en memoria.

Ejemplos:

- Conteo de resultados.
- Suma de ventas.
- Ventas agrupadas por período.
- Pruebas más solicitadas.
- Resultados por prueba.
- Métricas generales del laboratorio.

Para consultas específicas se utilizan JPQL projections, reduciendo la cantidad de información transferida entre la base de datos y la aplicación.

---

## 📑 Paginación

Las consultas que pueden producir grandes cantidades de información utilizan mecanismos de paginación.

Por ejemplo, el ranking de pruebas más solicitadas utiliza `Pageable` para limitar los resultados:

```java
Pageable pageable = PageRequest.of(0, 5);
```

Esto permite obtener únicamente los registros necesarios para determinadas vistas, como el Top 5 de pruebas solicitadas.

---

## 🧩 Diseño orientado al dominio

La aplicación está construida alrededor de las entidades y relaciones propias del negocio.

El modelo contempla conceptos como:

```text
Paciente
   │
   ├── Ordenes
   │       │
   │       └── Resultados
   │
   └── Historial

Ordenes
   │
   └── Resultados
           │
           └── Información de venta
```

Esto permite que las operaciones del sistema estén relacionadas con procesos reales del laboratorio y no únicamente con estructuras genéricas de almacenamiento.

---

## 🔐 Seguridad

La seguridad basada en autenticación y autorización se encuentra planificada como una de las siguientes fases del proyecto.

### Próximamente

- Autenticación de usuarios.
- Autorización basada en roles.
- Protección de endpoints.
- Control de acceso por funcionalidad.
- Gestión de sesiones/tokens.
- Protección de información sensible.

---

## 💾 Backups automáticos

Una funcionalidad planificada para una próxima versión es la implementación de backups automáticos de la base de datos.

El objetivo es permitir:

- Creación automática de backups locales.
- Configuración de intervalos de ejecución.
- Conservación de diferentes versiones.
- Identificación de la fecha y estado del backup.
- Recuperación ante errores o pérdida de información.

La funcionalidad estará especialmente orientada a instalaciones locales.

---

## 🖥️ Instalación local

Debido a que el sistema está destinado a un entorno donde la conexión a Internet puede ser inestable, se encuentra planificada una modalidad de instalación local.

### Próximamente

El objetivo es proporcionar un paquete que permita instalar el sistema directamente en el equipo del cliente.

La arquitectura prevista contempla:

```text
┌─────────────────────────┐
│      Aplicación local   │
│                         │
│  Angular / Frontend     │
│          │              │
│          ▼              │
│  Spring Boot / Backend  │
│          │              │
│          ▼              │
│       MySQL             │
└─────────────────────────┘
```

Se contempla empaquetar los componentes necesarios para facilitar la instalación y ejecución local, incluyendo el backend mediante JAR y una aplicación instalable para Windows mediante `.exe`.

Esto permitirá reducir la dependencia de una conexión permanente a Internet.

---

## 🚧 Roadmap

### Completado

- [x] Arquitectura full-stack.
- [x] Frontend con Angular.
- [x] Backend con Spring Boot.
- [x] Persistencia con MySQL.
- [x] Modelo de dominio.
- [x] Gestión de pacientes.
- [x] Gestión de pruebas.
- [x] Gestión de resultados.
- [x] Historial de pacientes.
- [x] Soft delete.
- [x] Activación/desactivación de registros.
- [x] Validaciones.
- [x] Manejo estructurado de errores HTTP.
- [x] Búsqueda y filtros.
- [x] Consultas mediante JPQL.
- [x] Projections.
- [x] Paginación.
- [x] Generación de reportes PDF.
- [x] Integración con WhatsApp Desktop.
- [x] Dashboard de métricas.
- [x] Visualización de ventas.
- [x] Visualización de resultados.
- [x] Pruebas más solicitadas.
- [x] Gráficos y tendencias.

### En desarrollo

- [ ] Backups automáticos.
- [ ] Sistema de versiones de backups.
- [ ] Configuración de intervalos de backup.
- [ ] Autenticación.
- [ ] Autorización.
- [ ] Roles y permisos.
- [ ] Protección de endpoints.
- [ ] Empaquetado para instalación local.
- [ ] Instalador `.exe`.
- [ ] Distribución del backend mediante `.jar`.
- [ ] Optimización para entornos con conectividad limitada.

---

## 📁 Estructura general

Una estructura conceptual del proyecto es:

```text
pro-emsalud/
│
├── frontend/
│   └── Angular
│       ├── components/
│       ├── pages/
│       ├── services/
│       ├── models/
│       └── ...
│
├── backend/
│   └── Spring Boot
│       ├── controller/
│       ├── service/
│       ├── repository/
│       ├── entity/
│       ├── dto/
│       ├── projection/
│       └── ...
│
└── database/
    └── MySQL
```

---

## 🔄 Flujo general de una operación

Un ejemplo del flujo de una consulta es:

```text
Usuario
   │
   ▼
Angular
   │
   │ HTTP Request
   ▼
Spring Boot Controller
   │
   ▼
Service
   │
   ▼
Repository
   │
   │ JPA / Hibernate
   ▼
MySQL
   │
   ▼
Repository
   │
   ▼
Service
   │
   ▼
Controller
   │
   │ HTTP Response
   ▼
Angular
   │
   ▼
Usuario
```

Este enfoque permite separar responsabilidades y facilitar el mantenimiento del sistema.

---

## 🧠 Aspectos técnicos destacados

El proyecto representa experiencia práctica trabajando con:

- Desarrollo full-stack.
- Arquitectura cliente-servidor.
- Desarrollo de APIs REST.
- Angular y TypeScript.
- Java y Spring Boot.
- Spring Data JPA.
- Hibernate.
- MySQL.
- Modelado relacional.
- JPQL.
- Projections.
- Paginación.
- Validación de datos.
- Manejo de errores.
- Soft delete.
- Integración entre frontend y backend.
- Generación de documentos.
- Visualización de datos.
- Consultas agregadas.
- Integración con aplicaciones externas.
- Diseño responsive.
- Desarrollo orientado a necesidades reales de negocio.
- Reuniones constantes con el cliente.

---

## 🚀 Objetivo del proyecto

El objetivo de Pro-Emsalud no es únicamente digitalizar procesos existentes, sino construir una plataforma que pueda evolucionar junto con las necesidades del laboratorio.

La arquitectura está diseñada para permitir la incorporación progresiva de nuevas funcionalidades, incluyendo seguridad, backups, instalación local y herramientas adicionales de análisis.

El proyecto busca combinar:

```text
Necesidad del negocio
        +
Automatización
        +
Integridad de datos
        +
Experiencia de usuario
        +
Información estratégica
        =
Sistema de gestión integral
```

---

## 📌 Estado del proyecto

- **Estado:** En desarrollo activo
- **Tipo:** Aplicación full-stack para un entorno real de negocio

**Stack principal:**

- Angular
- Spring Boot
- Java
- MySQL
- Hibernate / JPA
- TypeScript
- REST API
- Chart.js

---

## 👨‍💻 Autor

Desarrollado como proyecto de software orientado a resolver necesidades reales de operación y digitalización de procesos para un laboratorio clínico.

El proyecto representa experiencia práctica en el desarrollo, integración y mantenimiento de una aplicación full-stack utilizada en un entorno real.
