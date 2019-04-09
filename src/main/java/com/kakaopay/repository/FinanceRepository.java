package com.kakaopay.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kakaopay.model.Finance;

public interface FinanceRepository extends JpaRepository<Finance, Long> {
	
	@Query(nativeQuery=true,
				value="	SELECT	year AS year, SUM(amount) AS total_amount," + 
						"				SUM(CASE WHEN institute_code = 'hcf' THEN amount ELSE 0 END) AS hcf," + 
						"				SUM(CASE WHEN institute_code = 'kb' THEN amount ELSE 0 END) AS kb," + 
						"				SUM(CASE WHEN institute_code = 'wr' THEN amount ELSE 0 END) AS wr," + 
						"				SUM(CASE WHEN institute_code = 'sh' THEN amount ELSE 0 END) AS sh," + 
						"				SUM(CASE WHEN institute_code = 'ct' THEN amount ELSE 0 END) AS ct," + 
						"				SUM(CASE WHEN institute_code = 'hn' THEN amount ELSE 0 END) AS hn," + 
						"				SUM(CASE WHEN institute_code = 'nh' THEN amount ELSE 0 END) AS nh," + 
						"				SUM(CASE WHEN institute_code = 'keb' THEN amount ELSE 0 END) AS keb," + 
						"				SUM(CASE WHEN institute_code = 'etc' THEN amount ELSE 0 END) AS etc" + 
						"	FROM	finance" + 
						"	GROUP BY year")
	public List<Map<String, Integer>> getSumAmountByYear();
	
	@Query(nativeQuery=true,
				value="	SELECT	year AS year, max(i.institute_name) AS bank" + 
						"	FROM	finance AS f" + 
						"	JOIN		institute AS i" + 
						"	ON		f.institute_code = i.institute_code" + 
						"	GROUP BY year, f.institute_code" + 
						"	ORDER BY SUM(amount) DESC" + 
						"	LIMIT 1")
	public Map<String, String> getMaxAmountByYear();
	
	@Query(nativeQuery=true,
			value="	SELECT	year AS year, CAST(ROUND(AVG(CAST(amount AS FLOAT))) AS INT) AS amount, MAX(i.institute_name) AS bank" + 
					"	FROM	finance AS f" + 
					"	JOIN		institute AS i" + 
					"	ON		f.institute_code = i.institute_code" + 
					"	WHERE	f.institute_code= :instituteCode" + 
					"	GROUP BY year" + 
					"	ORDER BY amount ASC")
	public List<Map<String, String>> getAvgAmountByYear(@Param("instituteCode") String instituteCode);
}

