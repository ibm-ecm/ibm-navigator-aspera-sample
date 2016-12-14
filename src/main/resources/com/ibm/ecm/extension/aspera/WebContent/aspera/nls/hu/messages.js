/*
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2010, 2017
 * US Government Users Restricted Rights - Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

define({
		// NLS_CHARSET=UTF-8

		// configuration
		configuration_pane_aspera_url: "IBM Aspera kiszolgáló URL",
		configuration_pane_aspera_url_hover: "Adja meg az IBM Aspera kiszolgáló URL címét. Például: https://hosztnév:portszám/aspera/faspex",
		configuration_pane_aspera_url_prompt: "Erősen ajánlott, hogy a HTTPS protokollt használja.",
		configuration_pane_max_docs_to_send: "Az elküldendő elemek maximális száma",
		configuration_pane_max_docs_to_send_hover: "Adja meg a felhasználók által egyszerre küldhető elemek számát.",
		configuration_pane_max_procs_to_send: "Egyidejű kérések maximális száma",
		configuration_pane_max_procs_to_send_hover: "Adja meg a kérések maximális számát, amelyek egyszerre futhatnak.",
		configuration_pane_target_transfer_rate: "Cél átviteli sebesség (Mbps)",
		configuration_pane_target_transfer_rate_hover: "Adja meg a cél átvizeli sebességet megabit/másodpercben. A sebességet a licenc korlátozza. ",
		configuration_pane_speed_info: "A jelenlegi belépő szintű licenc 20 Mbps sebességet engedélyez. Gyorsabb Aspera Faspex kiértékelési licencre (10 Gbps-ig) az <a target='_blank' href='https://ibm.biz/BdjYHq'>Aspera kiértékelési változat igénylése</a> oldal segítségével frissíthet. ",

		// runtime
		send_dialog_sender_title: "Küldő: ${0}",
		send_dialog_not_set: "Nem beállított",
		send_dialog_send_one: "'${0}' küldése.",
		send_dialog_send_more: "${0} fájl küldése.",
		send_dialog_sender: "Felhasználói név:",
		send_dialog_password: "Jelszó:",
		send_dialog_missing_sender_message: "Meg kell adni egy felhasználónevet az IBM Aspera kiszolgálóra történő bejelentkezéshez.",
		send_dialog_missing_password_message: "Meg kell adni egy jelszót az IBM Aspera kiszolgálóra történő bejelentkezéshez.",
		send_dialog_title: "Küldés az IBM Aspera segítségével",
		send_dialog_missing_title_message: "Meg kell adnia egy címet.",
		send_dialog_info: "Fájlok küldése az IBM Aspera kiszolgálón keresztül, és felhasználók értesítése a fájlok elérhetőségéről.",
		send_dialog_recipients_label: "Címzettek:",
		send_dialog_recipients_textfield_hover_help: "Az e-mail címek és/vagy felhasználónevek elválasztására használjon vesszőket. Például: 'cím1, cím2, felhasználónév1, felhasználónév2'.",
		send_dialog_missing_recipients_message: "Meg kell adnia legalább egy e-mail címet vagy felhasználónevet.",
		send_dialog_title_label: "Cím:",
		send_dialog_note_label: "Adjon hozzá egy üzenetet.",
		send_dialog_earPassphrase_label: "Titkosítás jelszava:",
		send_dialog_earPassphrase_textfield_hover_help: "Adjon meg egy jelszót a fájlok titkosításához a kiszolgálón. Ezután a címzetteknek meg kell adniuk a jelszót a védett fájlok visszafejtéséhez a letöltés során.",
		send_dialog_notify_title: "Értesítés: ${0}",
		send_dialog_notifyOnUpload_label: "Értesítést kérek a fájl feltöltésekor",
		send_dialog_notifyOnDownload_label: "Értesítést kérek a fájl letöltésekor",
		send_dialog_notifyOnUploadDownload: "Értesítést kérek a fájl fel- és letöltésekor",
		send_dialog_send_button_label: "Küldés",
		send_dialog_started: "A(z) ${0} csomag küldése folyamatban van. ",
		status_started: "Csomag állapot: ${0} - Folyamatban van (${1}%)",
		status_stopped: "Csomag állapot: ${0} - Leállt",
		status_failed: "Csomag állapot: ${0} - Meghiúsult",
		status_completed: "Csomag állapot: ${0} - Befejeződött",

		// error
		send_dialog_too_many_items_error: "Az elemeket nem lehet elküldeni.",
		send_dialog_too_many_items_error_explanation: "Egyidejűleg legfeljebb ${0} elemet küldhet el. ${1} elem elküldését kísérelte meg.",
		send_dialog_too_many_items_error_userResponse: "Válasszon ki kevesebb elemet, és próbálkozzon újra az elemek elküldésével. Vagy keresse meg a rendszeradminisztrátort, hogy növelje meg az egyidejűleg elküldhető elemek maximális számát.",
		send_dialog_too_many_items_error_0: "elemek_maximális_száma",
		send_dialog_too_many_items_error_1: "elemek_száma",
		send_dialog_too_many_items_error_number: 5050,
});

