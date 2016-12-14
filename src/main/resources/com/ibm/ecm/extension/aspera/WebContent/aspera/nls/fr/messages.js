/*
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2010, 2017
 * US Government Users Restricted Rights - Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

define({
		// NLS_CHARSET=UTF-8

		// configuration
		configuration_pane_aspera_url: "URL de serveur IBM Aspera",
		configuration_pane_aspera_url_hover: "Entrez l'adresse URL du serveur IBM Aspera, par exemple : https://nom_hôte:numéro_port/aspera/faspex",
		configuration_pane_aspera_url_prompt: "Il est fortement recommandé d'utiliser le protocole HTTPS.",
		configuration_pane_max_docs_to_send: "Nombre maximal d'éléments à envoyer",
		configuration_pane_max_docs_to_send_hover: "Indiquez le nombre maximal d'éléments que les utilisateurs peuvent envoyer simultanément.",
		configuration_pane_max_procs_to_send: "Nombre maximal de demandes simultanées",
		configuration_pane_max_procs_to_send_hover: "Indiquez le nombre maximal de demandes pouvant s'exécuter simultanément.",
		configuration_pane_target_transfer_rate: "Débit de transfert cible (en Mbit/s)",
		configuration_pane_target_transfer_rate_hover: "Indiquez le débit de transfert cible en mégabits par seconde. Le débit est limité par la licence.",
		configuration_pane_speed_info: "Votre licence de base actuelle permet 20 Mbit/s. Vous pouvez passer à une licence d'évaluation plus rapide (jusqu'à 10 Gbit/s) pour Aspera Faspex en la demandant sur la page <a target='_blank' href='https://ibm.biz/BdjYHq'>Aspera Evaluation Request</a>.",

		// runtime
		send_dialog_sender_title: "Expéditeur : ${0}",
		send_dialog_not_set: "Non défini",
		send_dialog_send_one: "Envoyer '${0}'.",
		send_dialog_send_more: "Envoyer ${0} fichiers.",
		send_dialog_sender: "Nom d'utilisateur :",
		send_dialog_password: "Mot de passe :",
		send_dialog_missing_sender_message: "Pour vous connecter au serveur IBM Aspera, vous devez entrer un nom d'utilisateur.",
		send_dialog_missing_password_message: "Pour vous connecter au serveur IBM Aspera, vous devez entrer un mot de passe.",
		send_dialog_title: "Envoyer via IBM Aspera",
		send_dialog_missing_title_message: "Vous devez entrer un titre.",
		send_dialog_info: "Envoyer des fichiers via le serveur IBM Aspera et informer les utilisateurs qu'ils sont disponibles pour téléchargement.",
		send_dialog_recipients_label: "Destinataires :",
		send_dialog_recipients_textfield_hover_help: "Séparez les adresses e-mail et/ou les noms d'utilisateur par une virgule. Par exemple, entrez 'adresse1, adresse2, nomutil1, nomutil2'.",
		send_dialog_missing_recipients_message: "Vous devez indiquer au moins une adresse e-mail ou un nom d'utilisateur.",
		send_dialog_title_label: "Titre :",
		send_dialog_note_label: "Ajoutez un message.",
		send_dialog_earPassphrase_label: "Mot de passe de chiffrement :",
		send_dialog_earPassphrase_textfield_hover_help: "Entrez un mot de passe pour chiffrer les fichiers sur le serveur. Les destinataires seront ensuite tenus d'entrer le mot de passe pour déchiffrer les fichiers protégés lorsque ces derniers sont en cours de téléchargement.",
		send_dialog_notify_title: "Notification : ${0}",
		send_dialog_notifyOnUpload_label: "M'envoyer une notification lorsque le fichier est envoyé par téléchargement",
		send_dialog_notifyOnDownload_label: "M'envoyer une notification lorsque le fichier est reçu par téléchargement",
		send_dialog_notifyOnUploadDownload: "M'envoyer une notification lorsque le fichier est envoyé et reçu par téléchargement",
		send_dialog_send_button_label: "Envoyer",
		send_dialog_started: "Le package ${0} est en cours d'envoi.",
		status_started: "Statut du package : ${0} - En cours (${1}%)",
		status_stopped: "Statut du package : ${0} - Arrêté",
		status_failed: "Statut du package : ${0} - Echec",
		status_completed: "Statut du package : ${0} - Terminé",

		// error
		send_dialog_too_many_items_error: "Les éléments ne peuvent pas être envoyés.",
		send_dialog_too_many_items_error_explanation: "Vous pouvez envoyer jusqu'à ${0} éléments à la fois. Vous tentez d'envoyer ${1} éléments.",
		send_dialog_too_many_items_error_userResponse: "Sélectionnez moins d'éléments et tentez de les envoyer à nouveau. Vous pouvez également contacter votre administrateur système pour augmenter le nombre maximal d'éléments que vous pouvez envoyer à un moment donné.",
		send_dialog_too_many_items_error_0: "nombre_maximum_d'éléments",
		send_dialog_too_many_items_error_1: "nombre_d'éléments",
		send_dialog_too_many_items_error_number: 5050,
});

