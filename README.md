# Sistema de Gestión de Inversiones en Java

Este proyecto implementa un sistema completo para la gestión de una cartera de inversión.  
Permite registrar activos, realizar compras y ventas, consultar información financiera, generar informes y mantener la cartera almacenada mediante serialización.

## Funcionalidades principales

### 1. Carga y guardado de la cartera
- Lectura desde un archivo `.inv` que contiene un objeto `Cartera` serializado.
- Persistencia automática de la cartera al finalizar la ejecución.
- Manejo de excepciones por archivos inexistentes o errores de lectura/escritura.

### 2. Carga de activos desde archivo
- Importación desde un archivo `.csv`.
- Identificación y creación automática de:
  - Acciones
  - ETF
  - Fondos de inversión

### 3. Menú interactivo
El programa ofrece un menú mediante consola desde el cual es posible:
1. Registrar nuevos activos.  
2. Ingresar dinero en la cuenta asociada.  
3. Retirar dinero.  
4. Consultar información de la cuenta.  
5. Comprar activos.  
6. Vender activos.  
7. Cobrar dividendos.  
8. Generar informes de operaciones.  
9. Consultar operaciones entre fechas.  
10. Calcular rentabilidad actual.  
11. Calcular rentabilidad hasta una fecha concreta.  
12. Salir del programa.  
13. Generar informe detallado de operaciones.  
14. Generar informe de rentabilidad.

### 4. Clases principales (resumen)
- `Main.java`: Control principal del programa y menú interactivo.  
- `Cartera`: Gestiona el estado de la cuenta y las operaciones.  
- `Activo`, `Accion`, `ETF`, `FondoInversion`: Modelado de los productos financieros.  
- `GestionInversiones`: Lógica de operaciones e interacción con la cartera.  
- `Precio`: Obtención de precios simulados para activos.

