# Proyecto: Automatización de Pruebas

## Descripción

Repositorio contiene el proyecto de automatización correspondiente a la página web de uso público y gratuito [FakestoreAPI](https://fakestoreapi.com/docs). A continuación, se brinda información importante sobre el contenido del proyecto con el fin de orientar a las personas que deseen utilizar la implementación.

## Herramientas utilizadas

### Lenguaje de programación: Java

![Java](https://img.shields.io/badge/Java-21-orange)

Java es un lenguaje de programación y una plataforma de desarrollo creada por Sun Microsystems en 1995, y actualmente es propiedad de Oracle Corporation. Es un lenguaje orientado a objetos, de propósito general y con la característica principal de ser portátil gracias a la máquina virtual de Java (JVM), lo que permite que el mismo código pueda ejecutarse en diferentes sistemas operativos sin modificaciones.

---

### Gestor de dependencias: Maven

![Maven](https://img.shields.io/badge/Maven-3.8.1-blue)

Apache Maven es un gestor de dependencias y herramienta de automatización de proyectos en Java. Su función principal es facilitar la gestión de bibliotecas externas (dependencias) y la construcción de proyectos, asegurando que todo el código y sus librerías necesarias estén bien organizadas y actualizadas.

---

### Framework de automatización: Serenity

![Serenity](https://img.shields.io/badge/Serenity-3.9.8-blue)

Serenity BDD es un framework de automatización de pruebas que facilita la escritura de pruebas más estructuradas, legibles y mantenibles. Se usa principalmente para pruebas funcionales, de aceptación y automatización web en proyectos Java. Además, genera reportes detallados de las pruebas ejecutadas.

Serenity está diseñado para trabajar con Selenium WebDriver, Appium, Cucumber, JUnit y REST Assured, lo que permite realizar pruebas en aplicaciones web, móviles y API.

---

### Framework de pruebas: TestNG

![TestNG](https://img.shields.io/badge/TestNG-7.4.0-green)

TestNG (Test Next Generation) es un framework de pruebas para Java, diseñado para realizar pruebas unitarias, funcionales, de integración y automatizadas. Es una alternativa mejorada a JUnit, con características avanzadas como ejecución paralela, agrupación de pruebas, dependencias entre métodos y generación automática de reportes.

```
KataAutomatizacionIX-JorgeCalderon/
│── .github/
│   ├── workflows/                # Definición de pipelines de CI/CD en GitHub Actions
│   │   ├── serenity-ci.yml       # Workflow para ejecutar pruebas automatizadas
│── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── utils/           # Métodos reutilizables y configuración
│   │   │   ├── constants/       # Definición de constantes del proyecto
│   ├── test/
│   │   ├── java/
│   │   │   ├── tests/           # Clases de prueba que validan los flujos
│   │   │   ├── utilities/       # Metods reutilizbles
│   │   ├── resources/           # Recursos de pruebas
│── pom.xml                      # Gestión de dependencias con Maven
│── testNG.xml                   # Configuración de TestNG
│── pruebas.html                 # Reporte de ejecución de pruebas
│── .gitignore                   # Archivos ignorados en el repositorio
│── README.md                    # Documentación del proyecto

```

## Explicación del Workflow en GitHub Actions

El archivo `serenity-ci.yml` define un pipeline de CI/CD que se ejecuta en GitHub Actions. A continución, se detallan los pasos del flujo de trabajo:

```yml
   name: Serenity BDD Tests

on:
  push:
    branches:
      - main
  pull_request:
    branches:
      - main

jobs:
  tests:
    runs-on: ubuntu-latest

    steps:
      - name: 🛠️ Checkout del código
        uses: actions/checkout@v4

      - name: ☕ Configurar JDK 22
        uses: actions/setup-java@v4
        with:
          distribution: 'adopt'
          java-version: '22'
          cache: 'maven'

      - name: instalar dependencias con maven
        run: mvn install

      - name: 🚀 Ejecutar pruebas con Maven y Serenity
        run: mvn clean verify

      - name: 📄 Guardar reportes
        uses: actions/upload-artifact@v4
        with:
          name: extent-reports
          path: target/reports/pruebas.html
```

Cada sección configura el entorno, instala dependencias, ejecuta pruebas y sube los reportes generados.

## Contribución

Si deseas contribuir, por favor sigue el estándar de código y envía un **Pull Request**.

## Licencia

Este proyecto es de libre uso y distribución para quien desee utilizarlo.

## Referencias

Patrón de diseño page object model POM https://www.selenium.dev/documentation/test_practices/encouraged/page_object_models/

Guía de inicio rápido para GitHub Actions https://docs.github.com/es/actions/writing-workflows/quickstart

Repositorio de dependencias maven https://mvnrepository.com/
