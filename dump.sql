-- MySQL dump 10.13  Distrib 5.7.26, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: librarydb
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `book`
--

DROP TABLE IF EXISTS `book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `book` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `authorFirstName` varchar(40) DEFAULT NULL,
  `authorLastName` varchar(40) DEFAULT NULL,
  `daterelease` int(11) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `isbn` varchar(13) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `title` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book`
--

LOCK TABLES `book` WRITE;
/*!40000 ALTER TABLE `book` DISABLE KEYS */;
INSERT INTO `book` VALUES (1,'Joshua ','Bloch',2018,'Język Java jest konsekwentnie udoskonalany i unowocześniany dzięki zaangażowaniu wielu ludzi. Nowoczesny język Java staje się coraz bardziej wieloparadygmatowy, co oznacza, że stosowanie najlepszych praktyk w coraz większym stopniu determinuje jakość kodu. Obecnie napisanie kodu, który prawidłowo działa i może być łatwo zrozumiany przez innych programistów, nie wystarczy - należy zbudować program w taki sposób, aby można było go łatwo modyfikować. Jako że Java stała się obszerną i złożoną platformą, konieczne stało się uaktualnienie najlepszych praktyk.','2147483647','Wypożyczona','Java. Efektywne programowanie. Wydanie III'),(2,'Cay S. ','Horstmann',2016,'Książka Java. Podstawy od lat jest uznawana za najważniejszy praktyczny poradnik i podręcznik dla doświadczonych programistów dążących do doskonalenia swoich umiejętności w zakresie posługiwania się językiem Java. W wydaniu dziesiątym wprowadzono liczne uzupełnienia, które wiążą się z pojawieniem się bardzo oczekiwanej Javy SE. Przepisane i zmodyfikowane rozdziały obejmują swoim zakresem tematycznym nowe elementy platformy, idiomy i najlepsze praktyki. Znajdziesz w nich setki przykładowych programów, a wszystkie tak napisane, aby były łatwe do zrozumienia i wykorzystania w praktyce.','2147483647','Dostępna','Java. Podstawy. Wydanie X'),(3,' Paul i Harvey','Deitel',2018,'Tworzenie oprogramowania to wspaniała umiejętność. Zdolny programista może w zasadzie pracować w dowolnej dziedzinie. Co więcej, szalony rozwój technologii informatycznych właściwie codziennie otwiera nowe rynki i nowe nisze. Niemal wszędzie jest potrzebny procesor i oprogramowanie. I spora część tego cyfrowego torciku przypada programistom Javy. Najlepsze kąski dostają się jednak tym najlepszym, najzdolniejszym i najbardziej profesjonalnym.','2147483647','Dostępna','Programowanie w Javie. Solidna wiedza w praktyce. Wydanie XI'),(5,'Marcin','Płonkowski',2017,'Czy umiesz sobie wyobrazić świat urządzeń cyfrowych, w którym nagle zabrakło Androida? Czy wiesz, jak wiele codziennych czynności okazałoby się wówczas nie do zrobienia? Przez kilka ostatnich lat Android niepostrzeżenie ulokował się we wszystkich smartfonach czy tabletach, a aplikacje pisane dla tego systemu służą nam wszędzie: w kinie, w sklepie, na biwaku, na nartach i u lekarza. I wciąż potrzebujemy nowych! Jeśli chcesz dołączyć do grona innowacyjnych kreatorów, tworzących aplikacje ułatwiające i uprzyjemniające codzienne życie setek milionów ludzi, czym prędzej zapoznaj się z możliwościami Android Studio! To pozwoli Ci szybko zacząć przygodę z Javą i efektywnie pisać dobre programy.','2147483647','Zarezerwowana','Android Studio. Tworzenie aplikacji mobilnych'),(7,'Cay S.','Horstmann',2018,'Dziś Java jest uważana za starannie zaprojektowany i wciąż rozwijany język, który stanowi standard w wielu potężnych korporacjach z branży IT. W język ten wbudowano funkcje ułatwiające implementację wielu złożonych zadań programistycznych. W nowej wersji Javy znalazło się wiele usprawnień dotyczących najbardziej podstawowych technologii platformy Java. Nowe mechanizmy, na przykład modularyzacja czy nowe podejście do programowania współbieżnego, poprawią efektywność pracy programisty. Jednak opanowanie tak potężnego narzędzia i używanie go na profesjonalnym poziomie stało się prawdziwym wyzwaniem.','2147483647','Zarezerwowana','Java 9. Przewodnik doświadczonego programisty. Wydanie II'),(8,'Downey Allen B. ','Mayfield, Chris ',2017,'Zacznij myśleć jak programista! Naucz się łączyć umiejętności płynące z różnych dziedzin: matematyki, inżynierii i nauk przyrodniczych. Różnorodna wiedza ułatwi Ci pokonywanie przeszkód w pracy programisty — dzięki niej zdefiniujesz problem i sformułujesz jasne i precyzyjne rozwiązanie. Jak się okazuje, te wszystkie trudne umiejętności można sobie przyswoić, ucząc się programowania. Znajomość języka Java stanie się dodatkową korzyścią — jest to język o ugruntowanej pozycji, lubiany, dojrzały i najzwyczajniej bardzo przydatny.','2147483647','Dostępna','Myśl w języku Java! Nauka programowania'),(10,'Craig','Walls',2015,'Spring jest odpowiedzią na problemy trapiące programistów tworzących oprogramowanie przy użyciu EJB 2.x. Dzień, w którym został udostępniony szerokiemu gronu użytkowników, był punktem zwrotnym w historii języka Java. Od tej pory życie deweloperów stało się prostsze, a tworzenie nawet skomplikowanych aplikacji — zdecydowanie przyjemniejsze. Od tamtego czasu Spring jest wciąż rozwijany i oferuje coraz lepsze narzędzia programistom na całym świecie.','2147483647','Dostępna','Spring w akcji. Wydanie IV'),(11,'Mirosław J.','Kubiak',2018,'Trudno dziś poważnie myśleć o programowaniu bez porządnej znajomości języka Java. Aplikacje pisane w Javie są wykorzystywane w najróżniejszych okolicznościach, a sam język doskonale nadaje się także do zastosowań sieciowych. Jeśli zetknąłeś się już z tym językiem, znasz jego podstawowe elementy i nie jest Ci całkiem obce środowisko programistyczne NetBeans IDE, pora wziąć się za poważniejsze projekty. Doskonałą okazją do podciągnięcia się w pisaniu programów i wejścia na wyższy poziom wtajemniczenia będzie przerobienie wszystkich zadań z programowania, zaproponowanych w niniejszej książce.','2147483647','Dostępna','Java. Zadania z programowania z przykładowymi rozwiązaniami. Wydanie II'),(12,'Pierre-Yves','Saumont',2017,'Większość programistów pracuje zgodnie z paradygmatem programowania imperatywnego, który polega na tworzeniu ciągu instrukcji zmieniających stan programu. Najpoważniejszą wadą tej metody pracy jest podatność kodu na błędy, które trudno jest później wykryć i usunąć. Alternatywą jest programowanie funkcyjne — metodyka, która kładzie największy nacisk na stałe i funkcje. Takie programowanie polega na konstruowaniu funkcji oraz na obliczaniu wartości wyrażeń. W ten sposób otrzymuje się kod odporny na błędy. Niestety, nie zawsze można skorzystać z języków do programowania funkcyjnego.','2147483647','Dostępna','Java. Programowanie funkcyjne'),(54,'Brzechwa','Janek',2001,'Czarna magia – rodzaj praktyk magicznych stosowanych w złych intencjach lub opartych na wierze w możliwość odwoływania się do złej części sił demonicznych (jako że pierwotnie w części wierzeń etnicznych, np. Greków lub Słowian, demony miały charakter ambiwalentny), pojmowanych jako osobowe przeciwieństwa sił dobra i światła. Istnieje wiele rozbieżności w pojmowaniu różnicy pomiędzy białą a czarną magią. Większość[potrzebny przypis] współczesnych magów należących do zachodniego kręgu kulturowego żadnemu rodzajowi magii nie przypisuje kolorów, twierdząc, że magia jest tylko narzędziem, a sposób jej wykorzystania zależy wyłącznie od woli maga, czarownika, szamana. ','1234567890','Dostępna','Czarna Magia'),(61,'Henryk','Sienkiewicz',2001,'Janko Muzykant to nowela autorstwa Henryka Sienkiewicza. Po raz pierwszy ukazała się w \"Kurierze Warszawskim\" w 1879 roku[2].\r\nChłopiec grający na skrzypcach. Obraz z ok. 1630 r.\r\n\r\nJej akcja toczy się w XIX wieku, a głównym bohaterem jest chorowity chłopiec Janko. Imiona własne mieszkańców, język, wykonywane czynności, sposób spędzania czasu i funkcja dworu wskazują na to, że akcja noweli rozgrywa się w polskiej społeczności, która żyje pod zaborem rosyjskim','2023450159','Zarezerwowana','Janko Muzykant'),(62,'Charles','Perrault',1999,'„Tomcio Paluch” to pełna ciepła opowieść przez wielu dorosłych wspominana jako jedna z najważniejszych i najbardziej ukochanych lektur dzieciństwa. Teraz także ich pociechy będą mogły zapomnieć o całym świecie, śledząc losy malutkiego chłopca, który urodził się w rodzinie olbrzyma.','1239056781','Dostępna','Tomcio Paluch'),(73,'Blanka','Lipińska',2019,'Ciężarna Laura zostaje postrzelona. Najlepsi lekarze walczą o życie kobiety. Jej mąż, głowa sycylijskiej mafii, musi podjąć najtrudniejszą decyzję w swoim życiu - kogo ocalić: ukochaną czy ich dziecko... Jakiego wyboru dokona Massimo? Czy życie bez Laury będzie miało dla niego jeszcze sens? Czy będzie potrafił w pojedynkę wychować ich syna? Miliony myśli kłębią mu się w głowie, ale żadna nie przynosi ukojenia. Nie wie, jak potoczą się losy jego rodziny. Czyje 365 dni będziemy śledzić w trzeciej części sagi?','1111111111','Dostępna','Kolejne 365 dni'),(74,'Remigiusz','Mróz',2019,'Największe tajemnice drzemią w małych miasteczkach.\nDwadzieścia lat po śmierci ojca Kaja Burzyńska wciąż otrzymuje od niego wiadomości. Zadbał o to, przygotowując je zawczasu i zlecając coroczną wysyłkę tego samego, pozornie przypadkowego dnia. Po czasie Kaja traktuje to już jedynie jako zwyczajną tradycję – aż do momentu, gdy w listach zaczyna dostrzegać drugie dno…','1234567891','Wypożyczona','Listy zza grobu'),(75,'Mona','Kasten',2019,'Nikogo dotąd nie obdarzyła takim uczuciem.\r\nI nikt dotąd tak boleśnie jej nie zranił.\r\nRuby najbardziej chciałaby wrócić do dawnego życia, do czasu, gdy w elitarnym Maxton Hall nikt jej nie znał. Nie może jednak zapomnieć o Jamesie. Szczególnie że chłopak robi wszystko, co w jego mocy, by ją odzyskać.','0987654321','Dostępna','Save you'),(76,'Frederic','Martel',2018,'Kilkudziesięciu kardynałów oraz setki biskupów i księży zgodziło się rozmawiać z francuskim socjologiem i pisarzem Fredericem Martelem. Wśród nich znaleźli się zdeklarowani homoseksualiści, którzy odsłonili przed nim świat wielkiej szafy, w której ukrywają się księża geje, i dzięki którym poznał pilnie strzeżone tajemnice Watykanu.','2134563098','Dostępna','Sodoma. Hipokryzja i władza w Watykanie'),(77,'Patrick','Smith',2017,'Podróż samolotem to dla wielu z nas sprawa skomplikowana, kłopotliwa i często przerażająca, a ponadto spowita aurą tajemniczości. Od momentu, w którym przechodzimy przez drzwi terminala, atakują nas polecenia – stań tu, zdejmij buty tam, opróżnij kieszenie do tej kuwety, zapnij pas, zrób to, odłóż tamto – oraz lawina informacji. Większość dochodzi do nas nie bezpośrednio, lecz przez mikrofon; niektórych niedosłyszymy, innych nie zrozumiemy, jedne nas onieśmielają, innymi czujemy się zakłopotani.','0785648512','Dostępna','Pilot ci tego nie powie'),(78,'Ruth','Lillegraven',2019,'Clara jest ambitną i utalentowaną urzędniczką w Ministerstwie Sprawiedliwości. Robi karierę i nie chce pamiętać o dramatycznych wydarzeniach z dzieciństwa. Teraz całą uwagę skupia na pracy nad ustawą o ochronie dzieci – ofiar przemocy domowej. Do szpitala, w którym pracuje jej mąż, trafia skatowany czteroletni chłopiec. Tego samego wieczoru jego ojciec zostaje zamordowany. Podejrzenia padają na osobę najbliższą Clarze – Haavarda. To sprawia, że mroczna przeszłość Clary znów o sobie przypomina.','6548326904','Dostępna','Odbiorę ci wszystko'),(79,'Tomasz','Piątek',2019,'Tomasz Piątek zadaje sobie i nam pytanie - czy to cała prawda o obecnym premierze? Wielomiesięczna praca w archiwach (także IPN), jak również rozmowy z ludźmi, którzy znali Mateusza Morawieckiego na różnych etapach jego życia, a także skrupulatna analiza jego działalności oraz powiązań towarzyskich, politycznych i biznesowych - wszystko to daje inny obraz, niż ten, który znamy z rządowych czy prorządowych mediów.','9854372854','Dostępna','Morawiecki i jego tajemnice');
/*!40000 ALTER TABLE `book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dayOfBorrowDelay` int(11) DEFAULT NULL,
  `message` varchar(5000) DEFAULT NULL,
  `payForBorrow` decimal(19,2) DEFAULT NULL,
  `operation_id` int(11) DEFAULT NULL,
  `date` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK30wpjuitsyjia0tykk8pr0yif` (`operation_id`),
  CONSTRAINT `FK30wpjuitsyjia0tykk8pr0yif` FOREIGN KEY (`operation_id`) REFERENCES `operation` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `operation`
--

DROP TABLE IF EXISTS `operation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `operation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `author` varchar(255) DEFAULT NULL,
  `bookTitle` varchar(255) DEFAULT NULL,
  `endDate` date DEFAULT NULL,
  `operationDate` date DEFAULT NULL,
  `operationType` varchar(255) DEFAULT NULL,
  `startDate` date DEFAULT NULL,
  `userName` varchar(255) DEFAULT NULL,
  `book_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgbajdwas0l2dy3c5xeailxfsn` (`user_id`),
  KEY `FK50okr5lvdd93ugmt642w8mlk7` (`book_id`),
  CONSTRAINT `FK50okr5lvdd93ugmt642w8mlk7` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKgbajdwas0l2dy3c5xeailxfsn` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `operation`
--

LOCK TABLES `operation` WRITE;
/*!40000 ALTER TABLE `operation` DISABLE KEYS */;
INSERT INTO `operation` VALUES (55,'Płonkowski, Marcin','Android Studio. Tworzenie aplikacji mobilnych','2019-05-26','2019-05-23','RESERVATION','2019-05-23','Nowak, Mariusz',5,65),(56,'Mróz, Remigiusz','Listy zza grobu','1970-01-01','2019-05-23','BORROW','2019-05-23','Fronczewski, Piotr',74,86),(57,'Horstmann, Cay S.','Java 9. Przewodnik doświadczonego programisty. Wydanie II','2019-05-28','2019-05-25','RESERVATION','2019-05-25','Nowak, Mariusz',7,65),(58,'Sienkiewicz, Henryk','Janko Muzykant','2019-05-29','2019-05-26','RESERVATION','2019-05-26','Nowak, Mariusz',61,65);
/*!40000 ALTER TABLE `operation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `admin` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `firstName` varchar(255) DEFAULT NULL,
  `lastName` varchar(255) DEFAULT NULL,
  `login` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=111 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (10,'ADMIN','evdvw11@o2.pl','Jan','Kowalski','admin','$31$16$tIW_iX_9IxrjdpnYeCWNSjS72Os8TJxPbJjMo81-vtE','Aktywny'),(65,'USER','user@biblioteka.pl','Mariusz','Nowak','user','$31$16$AMvJV27JdrgxunHCQHRubPhFrzgkka1toGnDDSNhroc','Aktywny'),(84,'ADMIN','admin@biblioteka.pl','Jan','Winnicki','admin2','$31$16$N5vM07jFpMrtwp0cK4abCS7BRq8y_sLy8JqSlnfL7Ig','Aktywny'),(85,'USER','user1@wp.pl','Monika','Woś','user1','$31$16$VTo2WlSCrjR2bHX0-SLkDC2sVOTrOAZHfUp1TVlRW7s','Aktywny'),(86,'USER','user2@wp.pl','Piotr','Fronczewski','user2','$31$16$9U6nctj4ZNse8BFMSI0neTNBapi9qJVBHwGr00-R3b0','Aktywny'),(87,'USER','user3@wp.pl','Lech','Wiśniewski','user3','$31$16$W8D1F2pTCSJB-299mIPoUJQqIaZspER-KKfVvZNSi_A','Nieaktywny'),(88,'USER','user4@wp.pl','John','Smith','user4','$31$16$Q1Y1u3OZ7eLlHEjBRxINd77cPQWaiJGrEU5IY-_MJOc','Aktywny'),(100,'USER','user5@wp.pl','Beata','Kot','user5','$31$16$gjoY0jwTU8-YJ0paRHiK-tJyTIbjXl7RlYx2PjoRLTM','Aktywny'),(101,'USER','user6@wp.pl','Karol','Karolak','user6','$31$16$HWVWEnmiSGu_yRtxUv6taDQrezYXLAN_wqvr40ysez0','Aktywny'),(102,'USER','user7@wp.pl','Olga','Jankowska','user7','$31$16$Jtn8BepoEC_cce5xXblhNo8Za7kwj-FIQS-P9SeIPzA','Aktywny'),(104,'USER','temp1@temp.pl','Tomasz','Wojewoda','temp1','$31$16$bgrC5sRJzefnNsBO-TiPRQFhEcA5tIy_FFNF3uO7fzs','Nieaktywny'),(105,'USER','temp2@temp.pl','Janina','Nowacka','temp2','$31$16$kuwNw3TDl9nhhKwuBGSvZa6R5-X3dOiRslvVmNw3Y1I','Aktywny'),(106,'USER','temp3@temp.pl','Karol','Karol','temp3','$31$16$CpWKDa_Gj0O5fY1KqRyEh9rd-6AR6nKNzssiNDzZxco','Nieaktywny'),(107,'USER','temp4@temp.pl','Olga','Nowik','temp4','$31$16$_pADdEi2Nma-D7w6k9VPB4MngOVxFXXVuRvdQNbWUPI','Aktywny'),(108,'USER','temp5@temp.pl','Wojciech','Kulesza','temp5','$31$16$hH9sZTqZBnHPuB_Bkt-gz17yLCM7R8OSWa1m0NXz0o8','Aktywny'),(109,'USER','temp6@temp.pl','Paweł','Janas','temp6','$31$16$iTfZMc6sfM5qz-rEzQIYyDPX7adSvW1NmG-KM8CSPQk','Aktywny');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-05-28  8:11:11
