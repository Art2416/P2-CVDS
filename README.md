## Escuela Colombiana de Ingeniería

### CVDS – Parcial práctico
### Parcial Segundo Tercio

## Autor: David Arturo Narváez Lossa

## Solución 

1. Inicialmente clone el repositorio con el comando
~~~
git clone https://github.com/isanchezf/2022-2-par2t.git
~~~
![](./img/1.png)

2. Ya teniendo el repositorio de manera local, lo primero fue compilar con ayuda de maven 
~~~
mvn compile
~~~
![](./img/2.png)

3. Posteriormente probamos el servidor web local con el comando
~~~
mvn tomcat7:run
~~~
![](./img/3.png)
4. Una vez hecho esto, podiamos ver lo que aparece en nuestro navegador escribiendo en la barra de busqueda
~~~
http://localhost:8080/consultaPaciente.xhtml
~~~
![](./img/4.png)

aquí únicamente era visible el "titulo" puesto que la dirección estaba incompleta y para modificarlo debiamos poner
~~~
http://localhost:8080/faces/consultaPaciente.xhtml
~~~
5. Ahora vamos a implementar la primer consulta (consultarPacientePorId), para ello entramos a **PacienteMapper.xml** aquí realizamos la consulta de tipo sql, pero
en el select agregamos **parameterType** puesto que en esta primer consulta debemos ingresar dos parámetros **id** y **tipo_id**
![](./img/5.png)

## Historia de usuario #1

  -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
> **Como** Usuario de la plataforma de consultas médicas
>
> **Quiero** Poder consultar un paciente a partir de su número y tipo de identificación.
>
> **Para** Poder hacer una revisión de las consultas realizadas por un paciente cuyo documento ya conozco, y así evitar la búsqueda por el nombre del paciente.
>
> **Criterio de aceptación:** Se debe mostrar la fecha de nacimiento del paciente, su nombre, y cada una de las consultas realizadas. Las consultas deben estar organizadas de la más reciente (mostrados arriba) a la más antígua, y deben mostrar la fecha y el resúmen.

~~~
<select id="getPacienteById" resultMap="PacienteResult" parameterType="Map">
        SELECT
            p.id,
            p.tipo_id,
            p.fecha_nacimiento,
            p.nombre
        FROM
            PACIENTES as p
        WHERE
            p.id = #{id} AND p.tipo_id = #{tipo_id}
    </select>
~~~
6. En la carpeta de persistencia, modificamos la interfaz de mappers **PacienteMapper** con la nueva función **getPacienteById**
![](./img/6.png)
7. De igual manera lo hacemos tanto en la interfaz **DaoPaciente** y en la clase **MyBatisDAOPaciente**
![](./img/7.png)
![](./img/8.png)
8. En la parte de servicios modificamos la función **ConsultarPacientesPorId** en la clase de **ServiciosPacientempl** de manera que la interfaz emplea la función
**getPacienteById**
![](./img/9.png)

9. Para probar esta nueva consulta lo realizamos en **ServicesJUnitTest**  allí llamamos a nuestra función de **consultarPacientePorId** guardando previamente 
el nombre del paciente y lo verificamos con ayuda de un Assert.assertEquals; en este caso el nombre era **Carmenzo** y como ambos nombres son iguales está bien
~~~
 Paciente paciente = ServiciosPacientesFactory.getInstance().getTestingForumServices().consultarPacientesPorId(9876, TipoIdentificacion.TI);
 Assert.assertEquals(paciente.getNombre(),"Carmenzo");
~~~

NOTA: para realizar esto debemos cambiar la conexión como lo indica **config.properties** y realizando el cambio que nos indicó el profe donde la clave es: "prueba2019"

![](./img/conexion.png)

![](./img/10.png)

10. posteriormente actualizamos en los recursos web **consultaPaciente.xhtml** para conectar con el front y de igual manera lo hacemos en **managedbeans** 
en la clase **PacientesBean**

![](./img/11.png)
![](./img/12.png)

11. a continuación volvemos a **PacienteMapper.xml** para plantear la segunda consulta **consultarMenoresConEnfermedadContagiosa**, esta es un poco más compleja puesto que
se emplean las dos tablas **"PACIENTES"-"CONSULTAS"**

![](./img/13.PNG)

## Historia de usuario #2

  -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
> **Como** Usuario de la secretaría de salud de la plataforma
>
> **Quiero** Tener un reporte de las consultas de los menores de edad (menóres de 18 años) en las que en el resúmen se encuentren enfermedades contagiosas.
>
> **Para** Conocer con rapidez qué pacientes debo revisar y tomar medidas al respecto.
>
> **Criterio de aceptación:** El reporte NO debe requerir entrar parámetro alguno. Se considerán como enfermedades contagiosas: 'hepatitis' y 'varicela'. El reporte sólo debe contener el número y tipo de identificación  del paciente y la fecha de nacimiento, ordenados por edad de mayor a menor.
  -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


12. como lo hicimos en la consulta anterior debemos modificar la interfaz de mappers **PacienteMapper** con la nueva función **getMenoresEnfermedadContagiosa**

![](./img/14.PNG)

NOTA: en este caso de igual manera ponemos dos parámetros puesto que en la consulta verificamos el tipo de identificación para hacer la valuación de si es menor de edad o no

13. Implementamos de igual manera esta función **getMenoresEnfermedadContagiosa** en la interfaz de **DaoPaciente** y hacemos @override en la clase **MyBatisDAOPaciente.java**

![](./img/15.PNG)
![](./img/16.PNG)


Modelos necesarios para la realización de este parcial:

![](./img/Diagram.png)

![](./img/Model.png)

