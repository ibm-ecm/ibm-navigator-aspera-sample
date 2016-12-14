/*
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2010, 2017
 * US Government Users Restricted Rights - Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

define({
		// NLS_CHARSET=UTF-8

		// configuration
		configuration_pane_aspera_url: "Adresa URL serveru IBM Aspera",
		configuration_pane_aspera_url_hover: "Zadejte adresu URL serveru IBM Aspera. Příklad: https://název_hostitele:číslo_portu/aspera/faspex",
		configuration_pane_aspera_url_prompt: "Důrazně se doporučuje požít protokol HTTPS.",
		configuration_pane_max_docs_to_send: "Maximální počet odesílaných položek",
		configuration_pane_max_docs_to_send_hover: "Určete maximální počet položek, které uživatelé mohou najednou odeslat.",
		configuration_pane_max_procs_to_send: "Maximální počet souběžných požadavků",
		configuration_pane_max_procs_to_send_hover: "Určete maximální počet požadavků, které lze spustit současně.",
		configuration_pane_target_transfer_rate: "Cílová přenosová rychlost (v Mb/s)",
		configuration_pane_target_transfer_rate_hover: "Určete cílovou přenosovou rychlost v megabajtech za sekundu. Rychlost je omezena licencí.",
		configuration_pane_speed_info: "Vaše aktuální základní licence je pro rychlost 20 Mb/s. Upgradujte na rychlejší licenci pro vyhodnocení (až 10 Gb/s) pro produkt Aspera Faspex pomocí žádosti na stránce <a target='_blank' href='https://ibm.biz/BdjYHq'>Aspera Evaluation Request</a>.",

		// runtime
		send_dialog_sender_title: "Odesílatel: ${0}",
		send_dialog_not_set: "Nenastaveno",
		send_dialog_send_one: "Odeslat '${0}'",
		send_dialog_send_more: "Odeslat ${0} souborů",
		send_dialog_sender: "Jméno uživatele:",
		send_dialog_password: "Heslo:",
		send_dialog_missing_sender_message: "Abyste se přihlásili k serveru IBM Aspera, musíte zadat jméno uživatele.",
		send_dialog_missing_password_message: "Abyste se přihlásili k serveru IBM Aspera, musíte zadat heslo.",
		send_dialog_title: "Odeslat pomocí produktu IBM Aspera",
		send_dialog_missing_title_message: "Je třeba zadat název.",
		send_dialog_info: "Můžete odeslat soubory pomocí serveru IBM Aspera a upozornit uživatele, že jsou soubory k dispozici pro stažení.",
		send_dialog_recipients_label: "Příjemci:",
		send_dialog_recipients_textfield_hover_help: "E-mailové adresy a jména uživatelů lze oddělovat čárkami. Zadejte například 'adresa1, adresa2, uživatel1, uživatel2'.",
		send_dialog_missing_recipients_message: "Je třeba určit alespoň jednu e-mailovou adresu nebo jméno uživatele.",
		send_dialog_title_label: "Název:",
		send_dialog_note_label: "Přidejte zprávu.",
		send_dialog_earPassphrase_label: "Šifrovací heslo:",
		send_dialog_earPassphrase_textfield_hover_help: "Zadejte heslo pro šifrování souborů na serveru. Následně budou příjemci nuceni heslo zadat při dešifrování chráněných souborů během jejich stažení.",
		send_dialog_notify_title: "Upozornění: ${0}",
		send_dialog_notifyOnUpload_label: "Upozornit mě po odeslání souboru",
		send_dialog_notifyOnDownload_label: "Upozornit mě po stažení souboru",
		send_dialog_notifyOnUploadDownload: "Upozornit mě po odeslání a stažení souboru",
		send_dialog_send_button_label: "Odeslat",
		send_dialog_started: "Balík ${0} se odesílá.",
		status_started: "Stav balíku: ${0} - Probíhá (${1}%)",
		status_stopped: "Stav balíku: ${0} - Zastaveno",
		status_failed: "Stav balíku: ${0} - Selhání",
		status_completed: "Stav balíku: ${0} - Dokončeno",

		// error
		send_dialog_too_many_items_error: "Položky nelze odeslat.",
		send_dialog_too_many_items_error_explanation: "Najednou můžete odeslat až ${0} položek. Pokoušíte se odeslat ${1} položek.",
		send_dialog_too_many_items_error_userResponse: "Vyberte menší počet položek a zkuste znovu odeslat položky. Dále se můžete obrátit na administrátora systému kvůli zvýšení maximálního počtu položek, které lze najednou odeslat.",
		send_dialog_too_many_items_error_0: "maximální_počet_položek",
		send_dialog_too_many_items_error_1: "počet_položek",
		send_dialog_too_many_items_error_number: 5050,
});

