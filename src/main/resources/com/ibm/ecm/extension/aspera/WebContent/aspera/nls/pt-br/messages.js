/*
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2010, 2017
 * US Government Users Restricted Rights - Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

define({
		// NLS_CHARSET=UTF-8

		// configuration
		configuration_pane_aspera_url: "URL do servidor IBM Aspera",
		configuration_pane_aspera_url_hover: "Insira a URL do servidor IBM Aspera. Por exemplo: https://host_name:port_number/aspera/faspex",
		configuration_pane_aspera_url_prompt: "É altamente recomendado que você use o protocolo HTTPS.",
		configuration_pane_max_docs_to_send: "Número máximo de itens a serem enviados",
		configuration_pane_max_docs_to_send_hover: "Especifique o número máximo de itens que os usuários podem enviar por vez.",
		configuration_pane_max_procs_to_send: "Número máximo de pedidos simultâneos",
		configuration_pane_max_procs_to_send_hover: "Especifique o número máximo de solicitações que podem estar em execução ao mesmo tempo.",
		configuration_pane_target_transfer_rate: "Taxa de transferência de destino (em Mbps)",
		configuration_pane_target_transfer_rate_hover: "Especifique a taxa de transferência de destino em megabits por segundo. A taxa é limitada pela licença.",
		configuration_pane_speed_info: "Sua licença de nível de entrada atual é de 20 Mbps. Solicite uma licença de avaliação mais rápida (até 10 Gbps) para Aspera Faspex na página <a target='_blank' href='https://ibm.biz/BdjYHq'>Aspera Evaluation Request</a> para fazer o upgrade.",

		// runtime
		send_dialog_sender_title: "Emissor: ${0}",
		send_dialog_not_set: "Não configurado",
		send_dialog_send_one: "Enviar '${0}'.",
		send_dialog_send_more: "Enviar ${0} arquivos",
		send_dialog_sender: "Nome de usuário:",
		send_dialog_password: "Senha:",
		send_dialog_missing_sender_message: "Deve-se inserir um nome do usuário para efetuar login no servidor IBM Aspera.",
		send_dialog_missing_password_message: "Deve-se inserir uma senha para efetuar login no servidor IBM Aspera.",
		send_dialog_title: "Enviar por meio do IBM Aspera",
		send_dialog_missing_title_message: "Você deve inserir um título.",
		send_dialog_info: "Envie arquivos pelo servidor IBM Aspera e notifique os usuários de que os arquivos estejam disponíveis para download.",
		send_dialog_recipients_label: "Destinatários:",
		send_dialog_recipients_textfield_hover_help: "Use uma vírgula para separar os endereços de e-mail e/ou nomes de usuário. Por exemplo, insira 'address1, address2, username1, username2'.",
		send_dialog_missing_recipients_message: "Deve-se especificar pelo menos um endereço de e-mail ou nome de usuário.",
		send_dialog_title_label: "Título:",
		send_dialog_note_label: "Incluir uma mensagem.",
		send_dialog_earPassphrase_label: "Senha de Criptografia",
		send_dialog_earPassphrase_textfield_hover_help: "Insira uma senha para criptografar os arquivos no servidor. Subsequentemente, os destinatários precisarão inserir a senha para decriptografar os arquivos protegidos enquanto eles estiverem sendo transferidos por download.",
		send_dialog_notify_title: "Notificação: ${0}",
		send_dialog_notifyOnUpload_label: "Notificar-me quando o arquivo for transferido por upload",
		send_dialog_notifyOnDownload_label: "Notificar-me quando o arquivo for transferido por download",
		send_dialog_notifyOnUploadDownload: "Notificar-me quando o arquivo for transferido por upload e transferido por download",
		send_dialog_send_button_label: "Enviar",
		send_dialog_started: "O pacote ${0} está sendo enviado.",
		status_started: "Status do pacote: ${0} - Em andamento (${1}%)",
		status_stopped: "Status do pacote: ${0} - Interrompido",
		status_failed: "Status do pacote: ${0} - Com falha",
		status_completed: "Status do pacote: ${0} - Concluído",

		// error
		send_dialog_too_many_items_error: "Os itens não podem ser enviados.",
		send_dialog_too_many_items_error_explanation: "É possível enviar até ${0} itens por vez. Você está tentando enviar ${1} itens.",
		send_dialog_too_many_items_error_userResponse: "Selecione menos itens e tente enviar os itens novamente. Também é possível entrar em contato com o administrador do sistema para aumentar o número máximo de itens que podem ser enviados de uma vez.",
		send_dialog_too_many_items_error_0: "maximum_number_of_items",
		send_dialog_too_many_items_error_1: "number_of_items",
		send_dialog_too_many_items_error_number: 5050,
});

