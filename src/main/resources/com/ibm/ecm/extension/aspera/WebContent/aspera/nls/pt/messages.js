/*
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2010, 2017
 * US Government Users Restricted Rights - Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

define({
		// NLS_CHARSET=UTF-8

		// configuration
		configuration_pane_aspera_url: "URL do servidor do IBM Aspera ",
		configuration_pane_aspera_url_hover: "Introduza o URL do servidor do IBM Aspera. Por exemplo: https://host_name:port_number/aspera/faspex",
		configuration_pane_aspera_url_prompt: "É vivamente recomendado utilizar o protocolo HTTPS.",
		configuration_pane_max_docs_to_send: "Número máximo de itens a enviar",
		configuration_pane_max_docs_to_send_hover: "Especifique o número máximo de itens que os utilizadores podem enviar de cada vez.",
		configuration_pane_max_procs_to_send: "Número máximo de pedidos simultâneos",
		configuration_pane_max_procs_to_send_hover: "Especifique o número máximo de pedidos que podem ser executados ao mesmo tempo.",
		configuration_pane_target_transfer_rate: "Velocidade de transferência pretendida (in Mbps)",
		configuration_pane_target_transfer_rate_hover: "Especifique a velocidade de transferência pretendida em megabits por segundo. A velocidade está limitada pela licença.",
		configuration_pane_speed_info: "O nível de entrada da sua licença actual é para 20 Mbps. Actualize para uma licença de avaliação mais rápida (até 10 Gbps) para o Aspera Faspex, solicitando-o na página <a target='_blank' href='https://ibm.biz/BdjYHq'>Pedido de avaliação do Aspera</a>.",

		// runtime
		send_dialog_sender_title: "Remetente: ${0}",
		send_dialog_not_set: "Não definido",
		send_dialog_send_one: "Enviar '${0}'.",
		send_dialog_send_more: "Enviar ${0} ficheiros.",
		send_dialog_sender: "Nome de utilizador:",
		send_dialog_password: "Palavra-passe:",
		send_dialog_missing_sender_message: "Tem de introduzir um nome do utilizador para iniciar sessão no servidor do IBM Aspera.",
		send_dialog_missing_password_message: "Tem de introduzir uma palavra-passe para iniciar sessão no servidor do IBM Aspera.",
		send_dialog_title: "Enviar através do IBM Aspera",
		send_dialog_missing_title_message: "Tem de introduzir um título.",
		send_dialog_info: "Envie ficheiros através do servidor do IBM Aspera e notifique os utilizadores de que os ficheiros estão disponíveis para transferência.",
		send_dialog_recipients_label: "Destinatários:",
		send_dialog_recipients_textfield_hover_help: "Utilize uma vírgula para separar os endereços de correio electrónico e/ou nomes dos utilizadores. Por exemplo, introduza 'endereço1, endereço2, nomeutilizador1, nomeutilizador2'.",
		send_dialog_missing_recipients_message: "É necessário especificar, pelo menos, um endereço de correio electrónico ou nome do utilizador.",
		send_dialog_title_label: "Título:",
		send_dialog_note_label: "Adicione uma mensagem.",
		send_dialog_earPassphrase_label: "Palavra-passe da encriptação:",
		send_dialog_earPassphrase_textfield_hover_help: "Introduza uma palavra-passe para encriptar os ficheiros no servidor. Subsequentemente, será solicitado aos destinatários que introduzam a palavra-passe para desencriptar os ficheiros protegidos quando são descarregados.",
		send_dialog_notify_title: "Notificação: ${0}",
		send_dialog_notifyOnUpload_label: "Notificar-me quando o ficheiro estiver carregado",
		send_dialog_notifyOnDownload_label: "Notificar-me quando o ficheiro estiver descarregado",
		send_dialog_notifyOnUploadDownload: "Notificar-me quando o ficheiro estiver carregado e descarregado",
		send_dialog_send_button_label: "Enviar",
		send_dialog_started: "O pacote ${0} está a ser enviado.",
		status_started: "Estado do pacote: ${0} - Em curso (${1}%)",
		status_stopped: "Estado do pacote: ${0} - Parado",
		status_failed: "Estado do pacote: ${0} - Falha",
		status_completed: "Estado do pacote: ${0} - Concluído",

		// error
		send_dialog_too_many_items_error: "Não é possível enviar os itens.",
		send_dialog_too_many_items_error_explanation: "Pode enviar até ${0} itens de cada vez. Está a tentar enviar ${1} itens.",
		send_dialog_too_many_items_error_userResponse: "Seleccione menos itens e tente enviar os itens novamente. Também pode contactar o seu administrador do sistema para aumentar o número máximo de itens que pode enviar de cada vez.",
		send_dialog_too_many_items_error_0: "maximum_number_of_items",
		send_dialog_too_many_items_error_1: "number_of_items",
		send_dialog_too_many_items_error_number: 5050,
});

