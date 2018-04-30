package com.mascot.campuscloud.manager;

import java.text.Collator;
import java.time.LocalDateTime;
import java.util.Arrays;

import org.springframework.stereotype.Component;

import com.mascot.campuscloud.service.dto.Sortable;

/**
 * 对LocalFileDTO和LocalFolderDTO数组进行 按字母排序或者按最后修改时间排序
 */
@Component
public class Sorter {

	public Sortable[] sort(Sortable[] array, int sortType) {
		switch (sortType) {
		case 0:
			sortByAlpha(array);
			break;
		case 1:
			sortByTime(array);
			break;
		default:
			sortByAlpha(array);
		}
		return array;
	}

	private void sortByAlpha(Sortable[] array) {
		Arrays.sort(array, (s1, s2) -> {
			String name1 = s1.getLocalName();
			String name2 = s2.getLocalName();
			return Collator.getInstance(java.util.Locale.CHINA).compare(name1, name2);
		});
	}

	private void sortByTime(Sortable[] array) {
		Arrays.sort(array, (s1, s2) -> {
			LocalDateTime ldt1 = s1.getLdtModified();
			LocalDateTime ldt2 = s2.getLdtModified();
			return ldt1.compareTo(ldt2);
		});
	}
}
