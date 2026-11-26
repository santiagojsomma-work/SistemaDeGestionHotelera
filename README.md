# Sistema de Gestión Hotelera

**Trabajo Práctico Integrador — Programación 3**  
**Profesor:** Franco Borsani  
**Integrante:** Santiago Somma  
**Fecha:** Noviembre 2026

---

## 📋 Descripción

Sistema de gestión para una cadena de hoteles que administra reservas, habitaciones, huéspedes y empleados. Incluye generación masiva de datos (5000 registros), procesamiento ETL y reportes estadísticos.

## 🛠️ Tecnologías

- **Lenguaje:** Java SE 21
- **IDE:** Eclipse
- **Patrones de diseño:** Singleton, Builder, Observer, Facade
- **Herramientas de modelado:** app.diagrams.net

## 📁 Estructura del proyecto

| Carpeta | Contenido |
|---|---|
| `TP_Integrador/` | Código fuente Java (dominio, patrones, ETL, reportes) |
| `Diagrama_UML/` | Diagrama de clases UML en formato editable y PNG |
| `Documentacion/` | Informe PDF completo del desarrollo |
| `Capturas/` | Evidencias de actividades de cursada |

## 🚀 Ejecución

1. Importar `TP_Integrador/` en Eclipse como proyecto Java existente.
2. Ejecutar `TestGenerador.main()` para generar los archivos CSV y TXT.
3. Ejecutar `TestETL.main()` para transformar las fechas.
4. Ejecutar `TestReporte.main()` para ver los reportes estadísticos.
5. Ejecutar `TestDominio.main()` para validar los 5 casos del enunciado.

## 📚 Patrones de diseño aplicados

| Patrón | Aplicación |
|---|---|
| **Singleton** | Instancia única del `Hotel` |
| **Builder** | Construcción flexible de `Reserva` |
| **Observer** | Notificación de liberación de habitaciones a huéspedes |
| **Facade** | Interfaz simplificada para gestión de reservas |

## 🧪 Pruebas

Todos los tests pasan en verde:
- ✅ TestGenerador (4/4 pruebas)
- ✅ TestETL (5/5 pruebas)
- ✅ TestReporte (6/6 pruebas)
- ✅ TestDominio (5/5 casos: a, b, c, d, e)

## 📄 Licencia

Uso exclusivamente académico. Material desarrollado para la cursada de Programación 3.