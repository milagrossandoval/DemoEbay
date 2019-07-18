package pe.com.test.demoEbay;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import pe.com.test.demoEbay.page.BuscadorPage;
/*
 Script Demo Ebay
 Autor: Milagros Sandoval*/

@Test
public class BuscadorTest {

	private String urlInicial = "https://pe.ebay.com/";
	private BuscadorPage buscadorPage;

	@BeforeTest
	@Parameters({ "navegador", "remoto" })
	public void inicioClase(String navegador, int remoto) throws Exception {
		buscadorPage = new BuscadorPage(navegador, urlInicial, false);
	}

	public void buscarMarca() throws Exception {
		try {

			List<BuscadorTest.Item> listaValorObtenido = buscadorPage.buscar();
			List<BuscadorTest.Item> listaValorEsperado = new ArrayList<BuscadorTest.Item>(
					Arrays.asList(new BuscadorTest.Item[listaValorObtenido.size()]));
			Collections.copy(listaValorEsperado, listaValorObtenido);
			listaValorEsperado.sort(BuscadorTest.Item.ItemComparator);

			System.out.println("Take the first 5 products with their prices and print them in console.");
			List<String> valorObtenido = new ArrayList<>();
			for (int i = 0; i < 5; i++) {
				valorObtenido.add(listaValorObtenido.get(i).nombre);
				// double d =
				// listaValorObtenido.get(i).precio+listaValorObtenido.get(i).envio;
				// System.out.println("Producto: " +
				// listaValorObtenido.get(i).nombre + " - Precio: " + d);
				System.out.println("Producto: " + listaValorObtenido.get(i).nombre + " - Precio: "
						+ listaValorObtenido.get(i).precio);
			}

			//System.out.println("Valor Esperado");
			List<String> valorEsperado = new ArrayList<>();
			for (int i = 0; i < 5; i++) {
				valorEsperado.add(listaValorEsperado.get(i).nombre);
				// double d =
				// listaValorEsperado.get(i).precio+listaValorEsperado.get(i).envio;
				// System.out.println("Producto: " +
				// listaValorEsperado.get(i).nombre + " - Precio: " + d);
			}

			Assert.assertEquals(valorEsperado, valorObtenido);
			buscadorPage.ordenar();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	@AfterTest
	public void tearDown() throws Exception {
		buscadorPage.cerrarPagina();
	}

	public static class Item {

		private String nombre;
		private double precio;
		private double envio;

		public Item(String arg_nombre, double arg_precio, double arg_envio) {
			this.nombre = arg_nombre;
			this.precio = arg_precio;
			this.envio = arg_envio;
		}

		public String getNombre() {
			return nombre;
		}

		public void setNombre(String arg) {
			this.nombre = arg;
		}

		public double getPrecio() {
			return precio;
		}

		public void setPrecio(double arg) {
			this.precio = arg;
		}

		public double getEnvio() {
			return precio;
		}

		public void setEnvio(double arg) {
			this.precio = arg;
		}

		public int compareTo(BuscadorTest.Item o1) {
			if (this.precio + this.envio > o1.precio + o1.envio)
				return 1;
			if (this.precio + this.envio < o1.precio + o1.envio)
				return -1;
			return 0;
		}

		public static Comparator<BuscadorTest.Item> ItemComparator = new Comparator<BuscadorTest.Item>() {

			public int compare(BuscadorTest.Item item1, BuscadorTest.Item item2) {
				return item1.compareTo(item2);
			}

		};

	}

}
