package br.com.api.relatorio;

import java.io.IOException;
import java.util.Map;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

public abstract class convertJasperToPdf {

	private convertJasperToPdf() {
	}

	public static byte[] convertToPdf(Map<String, Object> params, String fileName) throws JRException, IOException {
		ResourcePatternResolver resolver = ResourcePatternUtils
				.getResourcePatternResolver(new PathMatchingResourcePatternResolver());

		String pattern = "classpath:/template/" + fileName;
		Resource resources = resolver.getResource(pattern);

		JasperReport report = JasperCompileManager.compileReport(resources.getInputStream());
		JasperPrint jasperPrint = JasperFillManager.fillReport(report, params, new JREmptyDataSource());

		return JasperExportManager.exportReportToPdf(jasperPrint);
	}
}