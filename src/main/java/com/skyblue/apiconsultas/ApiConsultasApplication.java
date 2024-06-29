package com.skyblue.apiconsultas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
@EnableCaching
public class ApiConsultasApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiConsultasApplication.class, args);

		List<Integer> listaOriginal = new ArrayList<>();
		listaOriginal.add(1000);
		listaOriginal.add(2000);
		listaOriginal.add(3000);

		// Aplicar el operador >>> a cada elemento de la lista
		List<Integer> listaNueva = new ArrayList<>();
		for (Integer num : listaOriginal) {
			int nuevoNum = num >>> 5; // Aplicar el operador >>> para dividir por 4
			listaNueva.add(nuevoNum); // Agregar el nuevo número a la lista nueva
		}

		// Mostrar la lista original y la lista nueva
		System.out.println("Lista original: " + listaOriginal);
		System.out.println("Lista nueva después de aplicar el operador >>>: " + listaNueva);
	}


}
