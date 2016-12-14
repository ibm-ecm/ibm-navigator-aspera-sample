/*
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2010, 2017
 * US Government Users Restricted Rights - Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

define({
		// NLS_CHARSET=UTF-8

		// configuration
		configuration_pane_aspera_url: "Διεύθυνση URL εξυπηρετητή IBM Aspera",
		configuration_pane_aspera_url_hover: "Καταχωρήστε τη διεύθυνση URL του εξυπηρετητή IBM Aspera. Για παράδειγμα, https://όνομα_υπολογιστή:αριθμός_θύρας/aspera/faspex.",
		configuration_pane_aspera_url_prompt: "Συνιστάται η χρήση του πρωτοκόλλου HTTPS.",
		configuration_pane_max_docs_to_send: "Μέγιστος αριθμός στοιχείων για αποστολή",
		configuration_pane_max_docs_to_send_hover: "Ορίστε το μέγιστο αριθμό στοιχείων που θα μπορούν να αποστέλλουν ταυτόχρονα οι χρήστες.",
		configuration_pane_max_procs_to_send: "Μέγιστος αριθμός ταυτόχρονων αιτήσεων",
		configuration_pane_max_procs_to_send_hover: "Ορίστε το μέγιστο αριθμό αιτήσεων που θα μπορούν να εκτελούνται ταυτόχρονα.",
		configuration_pane_target_transfer_rate: "Στόχος για την ταχύτητα μεταφοράς (σε Mbps)",
		configuration_pane_target_transfer_rate_hover: "Καθορίστε το στόχο για την ταχύτητα μεταφοράς σε megabits ανά δευτερόλεπτο. Η ταχύτητα περιορίζεται από την άδεια χρήσης.",
		configuration_pane_speed_info: "Η ταχύτητα που αντιστοιχεί στην τρέχουσα άδεια βασικού επιπέδου είναι 20 Mbps. Μπορείτε να αναβαθμίσετε την άδεια σε μια άδεια αξιολόγησης (με ταχύτητες έως 10 Gbps) για το Aspera Faspex υποβάλλοντας σχετική αίτηση στην ιστοσελίδα <a target='_blank' href='https://ibm.biz/BdjYHq'>Aspera Evaluation Request</a>.",

		// runtime
		send_dialog_sender_title: "Αποστολέας: ${0}",
		send_dialog_not_set: "Δεν έχει οριστεί",
		send_dialog_send_one: "Αποστολή του αρχείου '${0}'",
		send_dialog_send_more: "Αποστολή ${0} αρχείων",
		send_dialog_sender: "Όνομα χρήστη:",
		send_dialog_password: "Κωδικός πρόσβασης:",
		send_dialog_missing_sender_message: "Πρέπει να ορίσετε ένα όνομα χρήστη για να συνδεθείτε στον εξυπηρετητή IBM Aspera.",
		send_dialog_missing_password_message: "Πρέπει να ορίσετε έναν κωδικό πρόσβασης για να συνδεθείτε στον εξυπηρετητή IBM Aspera.",
		send_dialog_title: "Αποστολή μέσω IBM Aspera",
		send_dialog_missing_title_message: "Πρέπει να καταχωρήσετε έναν τίτλο.",
		send_dialog_info: "Στείλτε αρχεία μέσω του εξυπηρετητή IBM Aspera και ειδοποιήστε τους χρήστες ότι τα αρχεία είναι έτοιμα για μεταφόρτωση.",
		send_dialog_recipients_label: "Παραλήπτες:",
		send_dialog_recipients_textfield_hover_help: "Χρησιμοποιήστε κόμμα για το διαχωρισμό των διευθύνσεων e-mail ή/και των ονομάτων χρηστών. Για παράδειγμα, 'διεύθυνση_1, διεύθυνση_2, όνομα_χρήστη_1, όνομα_χρήστη_2.",
		send_dialog_missing_recipients_message: "Πρέπει να ορίσετε τουλάχιστον μία διεύθυνση e-mail ή ένα όνομα χρήστη.",
		send_dialog_title_label: "Τίτλος:",
		send_dialog_note_label: "Προσθέστε ένα μήνυμα.",
		send_dialog_earPassphrase_label: "Κωδικός πρόσβασης κρυπτογράφησης:",
		send_dialog_earPassphrase_textfield_hover_help: "Ορίστε έναν κωδικό πρόσβασης για την κρυπτογράφηση των αρχείων στον εξυπηρετητή. Οι παραλήπτες που μεταφορτώνουν αρχεία από τον εξυπηρετητή θα πρέπει να χρησιμοποιούν αυτό τον κωδικό πρόσβασης για την αποκρυπτογράφηση των αρχείων.",
		send_dialog_notify_title: "Ειδοποίηση: ${0}",
		send_dialog_notifyOnUpload_label: "Να ειδοποιούμαι για τη μεταφόρτωση του αρχείου στον εξυπηρετητή",
		send_dialog_notifyOnDownload_label: "Να ειδοποιούμαι για τη μεταφόρτωση του αρχείου από τον εξυπηρετητή",
		send_dialog_notifyOnUploadDownload: "Να ειδοποιούμαι για τη μεταφόρτωση του αρχείου από και προς τον εξυπηρετητή",
		send_dialog_send_button_label: "Αποστολή",
		send_dialog_started: "Το πακέτο ${0} αποστέλλεται.",
		status_started: "Κατάσταση πακέτου: ${0} - Σε εξέλιξη (${1}%)",
		status_stopped: "Κατάσταση πακέτου: ${0} - Τερματίστηκε",
		status_failed: "Κατάσταση πακέτου: ${0} - Απέτυχε",
		status_completed: "Κατάσταση πακέτου: ${0} - Ολοκληρώθηκε",

		// error
		send_dialog_too_many_items_error: "Δεν είναι δυνατή η αποστολή των στοιχείων.",
		send_dialog_too_many_items_error_explanation: "Μπορείτε να στείλετε έως ${0} στοιχεία κάθε φορά. Προσπαθήσατε να στείλετε ${1} στοιχεία.",
		send_dialog_too_many_items_error_userResponse: "Επιλέξτε λιγότερα στοιχεία για αποστολή και προσπαθήστε ξανά. Μπορείτε επίσης να επικοινωνήσετε με το διαχειριστή συστήματος για να του ζητήσετε να αυξήσει το μέγιστο αριθμό στοιχείων που μπορούν να αποστέλλονται ταυτόχρονα.",
		send_dialog_too_many_items_error_0: "maximum_number_of_items",
		send_dialog_too_many_items_error_1: "number_of_items",
		send_dialog_too_many_items_error_number: 5050,
});

