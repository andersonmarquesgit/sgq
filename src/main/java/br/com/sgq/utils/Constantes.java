package br.com.sgq.utils;

public abstract class Constantes {
	
	/** 
	 * CONSTANTES GENÉRICAS
	 */
	public static final String OUTCOME_NIVEL_DE_USUARIO = "configuracoes";
	public static final int QTD_ZEROS_NUM_RECLAMACAO = 5;
	public static final int QTD_ZEROS_NUM_ANALISE_CRITICA = 2;
	public static final String NA = "N/A";
	
	/** 
	 * CAMINHO DOS RELATÓRIOS
	 */
	public static final String CAMINHO_RELATORIO_RECLAMACAO = "/relatorios/report-reclamacao.jrxml";
	
	/** 
	 * CONFIGURAÇÕES DO EMAIL DO SISTEMA
	 */
	public static final String EMAIL_SMTP = "email.smtp";
	public static final String EMAIL_SMTP_PORTA = "email.smtp.porta";
	public static final String EMAIL_USUARIO = "email.user";
	public static final String EMAIL_SENHA = "email.password";
	public static final String EMAIL_REMETENTE = "email.remetente.email";
	public static final String EMAIL_REMETENTE_NOME = "email.remetente.name";
	
	/** 
	 * CAMINHO LOGO EMPRESA PARA EMAIL
	 */
	public static final String CAMINHO_LOGO_EMPRESA = "/imagens/empresa.png";
	
}
