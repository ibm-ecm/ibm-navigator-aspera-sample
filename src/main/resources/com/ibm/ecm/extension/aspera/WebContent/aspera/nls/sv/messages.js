/*
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2010, 2017
 * US Government Users Restricted Rights - Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

define({
		// NLS_CHARSET=UTF-8

		// configuration
		configuration_pane_aspera_url: "IBM Aspera-serverns URL",
		configuration_pane_aspera_url_hover: "Ange URL-adressen till IBM Aspera-servern. Exempel: https://host_name:port_number/aspera/faspex",
		configuration_pane_aspera_url_prompt: "Det rekommenderas starkt att du använder HTTPS-protokollet.",
		configuration_pane_max_docs_to_send: "Maximalt antal objekt att skicka",
		configuration_pane_max_docs_to_send_hover: "Ange maximalt antal objekt som användare kan skicka åt gången.",
		configuration_pane_max_procs_to_send: "Maximalt antal samtidiga begäran",
		configuration_pane_max_procs_to_send_hover: "Ange det maximala antalet begäran som kan köras samtidigt.",
		configuration_pane_target_transfer_rate: "Målöverföringshastighet (i Mbit/s)",
		configuration_pane_target_transfer_rate_hover: "Ange målöverföringshastigheten i megabit per sekund. Hastigheten begränsas av licensen.",
		configuration_pane_speed_info: "Din aktuella licens på ingångsnivå är för 20 Mbit/s. Uppgradera till en snabbare utvärderingslicens (upp till 10 Gbit/s) för Aspera Faspex genom att begära den på sidan <a target='_blank' href='https://ibm.biz/BdjYHq'>Aspera Evaluation Request</a>.",

		// runtime
		send_dialog_sender_title: "Avsändare: ${0}",
		send_dialog_not_set: "Inte angiven",
		send_dialog_send_one: "Skicka '${0}'.",
		send_dialog_send_more: "Skicka ${0} filer.",
		send_dialog_sender: "Användarnamn:",
		send_dialog_password: "Lösenord:",
		send_dialog_missing_sender_message: "Du måste ange ett användarnamn för att logga in till IBM Aspera-servern.",
		send_dialog_missing_password_message: "Du måste ange ett lösenord för att logga in till IBM Aspera-servern.",
		send_dialog_title: "Skicka via IBM Aspera",
		send_dialog_missing_title_message: "Du måste ange ett namn.",
		send_dialog_info: "Skicka filer via the IBM Aspera-server och avisera användarna om att filerna är tillgängliga för hämtning.",
		send_dialog_recipients_label: "Mottagare:",
		send_dialog_recipients_textfield_hover_help: "Använd ett komma som avgränsare mellan e-postadresser och/eller användarnamn. Exempel: 'adress1, adress2, användarnamn1, användarnamn2'.",
		send_dialog_missing_recipients_message: "Du måste ange minst en e-postadress eller ett användarnamn.",
		send_dialog_title_label: "Titel:",
		send_dialog_note_label: "Lägg till ett meddelande.",
		send_dialog_earPassphrase_label: "Krypteringslösenord:",
		send_dialog_earPassphrase_textfield_hover_help: "Ange ett lösenord för att kryptera filerna på servern. Därefter måste mottagarna ange lösenordet för att avkryptera skyddade filer när de hämtas.",
		send_dialog_notify_title: "Avisering: ${0}",
		send_dialog_notifyOnUpload_label: "Meddela mig när filen är överförd",
		send_dialog_notifyOnDownload_label: "Meddela mig när filen är hämtad",
		send_dialog_notifyOnUploadDownload: "Meddela mig när filen är överförd och hämtad",
		send_dialog_send_button_label: "Skicka",
		send_dialog_started: "Paketet ${0} skickas.",
		status_started: "Paketstatus: ${0} - Pågår (${1}%)",
		status_stopped: "Paketstatus: ${0} - Stoppat",
		status_failed: "Paketstatus: ${0} - Misslyckades",
		status_completed: "Paketstatus: ${0} - Slutfördes",

		// error
		send_dialog_too_many_items_error: "Det går inte att skicka objekten.",
		send_dialog_too_many_items_error_explanation: "Du kan skicka upp till ${0} objekt åt gången. Du försöker skicka ${1} objekt.",
		send_dialog_too_many_items_error_userResponse: "Välj färre objekt och gör ett nytt försök att skicka objekten. Du kan också be systemadministratören öka det maximala antalet objekt som du kan skicka åt gången.",
		send_dialog_too_many_items_error_0: "maximalt_antal_objekt",
		send_dialog_too_many_items_error_1: "antal_objekt",
		send_dialog_too_many_items_error_number: 5050,
});

