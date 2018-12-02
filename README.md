# crtm-cards-java

[![CircleCI](https://circleci.com/gh/jamezrin/crtm-cards-java/tree/master.svg?style=svg)](https://circleci.com/gh/jamezrin/crtm-cards-java/tree/master)
[![](https://jitpack.io/v/jamezrin/crtm-cards-java.svg)](https://jitpack.io/#jamezrin/crtm-cards-java)

Este proyecto es una API para consultar el estado de tarjetas de transporte del CRTM.
Hace peticiones a `https://www.tarjetatransportepublico.es/CRTM-ABONOS/consultaSaldo.aspx`, 
analiza la respuesta y devuelve objetos representando cada tipo de información que devolvería.

## Maven

Paso 1. Añade este repositorio dentro de `repositories` de tu archivo `pom.xml`
```xml
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>
```

Paso 2. Añade esta librería dentro de `dependencies` de tu archivo `pom.xml`
```xml
<dependency>
    <groupId>com.github.jamezrin</groupId>
    <artifactId>crtm-cards-java</artifactId>
    <version>v1.1.1</version>
</dependency>
```

## Uso

Se pueden ver ejemplos de como usar esta librería en las pruebas de integración, 
en concreto [TestScrapCrtm](/src/test/java/TestScrapCrtm.java)
