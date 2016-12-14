/*
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2010, 2017
 * US Government Users Restricted Rights - Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

define({
		// NLS_CHARSET=UTF-8

		// configuration
		configuration_pane_aspera_url: "Adres URL serwera IBM",
		configuration_pane_aspera_url_hover: "Wprowadź adres URL serwera IBM Aspera. Na przykład: https://nazwa_hosta:numer+portu/aspera/faspex",
		configuration_pane_aspera_url_prompt: "Wysoce zalecane jest korzystanie z protokołu HTTPS.",
		configuration_pane_max_docs_to_send: "Maksymalna liczba elementów do wysłania.",
		configuration_pane_max_docs_to_send_hover: "Określ maksymalną liczbę elementów, jaką użytkownicy mogą wysłać jednorazowo.",
		configuration_pane_max_procs_to_send: "Maksymalna liczba współbieżnych żądań",
		configuration_pane_max_procs_to_send_hover: "Określ maksymalną liczbę żądań, jakie mogą być uruchomione w tym samym czasie.",
		configuration_pane_target_transfer_rate: "Docelowa szybkość przesyłania (w Mb/s)",
		configuration_pane_target_transfer_rate_hover: "Określ docelową szybkość przesyłania w megabitach na sekundę. Szybkość jest ograniczona warunkami licencji.",
		configuration_pane_speed_info: "Twoja obecna licencja podstawowa ogranicza szybkość przesyłania do 20 Mb/s. Przejdź na szybszą licencję próbną (do 10 Gb/s) na Aspera Faspex. Możesz ją zamówić na stronie <a target='_blank' href='https://ibm.biz/BdjYHq'>Wniosku o próbne używanie rozwiązania Aspera</a>.",

		// runtime
		send_dialog_sender_title: "Nadawca: ${0}",
		send_dialog_not_set: "Nie ustawiono",
		send_dialog_send_one: "Wyślij „${0}”.",
		send_dialog_send_more: "Wyślij ${0} pliki/-ów.",
		send_dialog_sender: "Nazwa użytkownika:",
		send_dialog_password: "Hasło:",
		send_dialog_missing_sender_message: "Musisz podać nazwę użytkownika, aby zalogować się do serwera IBM Aspera.",
		send_dialog_missing_password_message: "Musisz podać hasło, aby zalogować się do serwera IBM Aspera.",
		send_dialog_title: "Wyślij przez IBM Aspera",
		send_dialog_missing_title_message: "Musisz wprowadzić tytuł.",
		send_dialog_info: "Wyślij pliki przez serwer IBM Aspera i powiadom użytkowników, że pliki są dostępne do pobrania.",
		send_dialog_recipients_label: "Odbiorcy:",
		send_dialog_recipients_textfield_hover_help: "Do oddzielenia adresów e-mail i/lub nazw użytkowników użyj przecinków. Na przykład wprowadź 'adres1, adres2, nazwaużytkownika1, nazwaużytkownika2'.",
		send_dialog_missing_recipients_message: "Należy określić co najmniej jeden adres e-mail lub jedną nazwę użytkownika.",
		send_dialog_title_label: "Tytuł:",
		send_dialog_note_label: "Dodaj wiadomość.",
		send_dialog_earPassphrase_label: "Hasło szyfrujące:",
		send_dialog_earPassphrase_textfield_hover_help: "Wprowadź hasło, aby zaszyfrować pliki na serwerze. Następnie odbiorcy będą musieli wprowadzić to hasło, aby odszyfrować chronione pliki podczas ich pobierania.",
		send_dialog_notify_title: "Powiadomienie: ${0}",
		send_dialog_notifyOnUpload_label: "Powiadom mnie o przesłaniu pliku",
		send_dialog_notifyOnDownload_label: "Powiadom mnie o pobraniu pliku",
		send_dialog_notifyOnUploadDownload: "Powiadom mnie o przesłaniu i pobraniu pliku",
		send_dialog_send_button_label: "Wyślij",
		send_dialog_started: "Trwa wysyłanie pakietu ${0}.",
		status_started: "Status pakietu: ${0} - w toku (${1}%)",
		status_stopped: "Status pakietu: ${0} - zatrzymano",
		status_failed: "Status pakietu: ${0} - niepowodzenie",
		status_completed: "Status pakietu: ${0} - ukończono",

		// error
		send_dialog_too_many_items_error: "Nie można wysłać tych elementów.",
		send_dialog_too_many_items_error_explanation: "Jednorazowo można wysłać maksymalnie ${0} elementów. Podejmujesz próbę dodania ${1} elementów.",
		send_dialog_too_many_items_error_userResponse: "Wybierz mniejszą liczbę elementów i ponów próbę ich wysłania. Możesz również zwrócić się do administratora systemu, aby zwiększył maksymalną liczbę elementów, jaką można wysłać jednorazowo.",
		send_dialog_too_many_items_error_0: "maksymalna_liczba_elementów",
		send_dialog_too_many_items_error_1: "liczba_elementów",
		send_dialog_too_many_items_error_number: 5050,
});

