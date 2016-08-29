package br.com.sgq.utils;

import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ComparableComparator;
import org.apache.commons.collections.comparators.ComparatorChain;

public class OrdenacaoUtil {
	private OrdenacaoUtil() {
	}

	/**
	 * Ordena a lista de acordo com as propriedades informadas.
	 *
	 * @param lista Parâmetro a ser ordenado.
	 * @param decrescente Informar se crescente ou decrescente.
	 * @param propriedades Propriedades que serão ordenadas.
	 */
	@SuppressWarnings("unchecked")
	public static <T> void ordenarLista(List<T> lista, boolean decrescente, String... propriedades) {
	        Comparator<T> comparator = new Comparator<T>() {
	                @Override
	                public int compare(T primeiro, T segundo) {
	                        if (primeiro != null && segundo != null) {
	                                if (primeiro instanceof String && segundo instanceof String) {
	                                        return Collator.getInstance(new Locale("pt/BR")).compare((String)primeiro, (String) segundo);
	                                }
	                                return ComparableComparator.getInstance().compare(primeiro, segundo);
	                        }
	                        return -1;
	                }
	        };
	        ComparatorChain comparatorChain = new ComparatorChain();
	        for (String propriedade : propriedades) {
	                comparatorChain.addComparator(new BeanComparator(propriedade, comparator), decrescente);
	        }
	        Collections.sort(lista, comparatorChain);
	}
}
