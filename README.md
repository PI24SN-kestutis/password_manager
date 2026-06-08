# Slaptažodžių tvarkyklė

## Aprašymas

„Slaptažodžių tvarkyklė“ – tai JavaFX darbalaukio programa, skirta saugiam slaptažodžių saugojimui, paieškai, atnaujinimui ir šalinimui.

Programa naudoja:

* AES šifravimą slaptažodžių apsaugai;
* AES šifravimą viso duomenų failo apsaugai;
* Base64 kodavimą užšifruotų duomenų saugojimui tekstiniame faile;
* CSV formatą duomenų saugojimui.

Projektas sukurtas studijų darbui, siekiant praktiškai pritaikyti kriptografijos ir saugaus duomenų saugojimo principus.

---

## Naudotos technologijos

* Java 21
* JavaFX
* Maven
* javax.crypto
* AES (Advanced Encryption Standard)
* Base64

---

## Projekto struktūra

```text
data/
├── aes.key
├── passwords.csv
└── passwords.enc

src/
├── model/
├── service/
├── controller/
└── util/
```

---

## Programos paleidimas

### Reikalavimai

* Java JDK 21 arba naujesnė
* Maven 3.9+
* IntelliJ IDEA

### Projekto atsisiuntimas

```bash
git clone https://github.com/JUSU_VARTOTOJAS/slaptazodziu_tvarkykle.git
```

### Projekto paleidimas

```bash
mvn clean javafx:run
```

arba paleisti klasę:

```java
Main.java
```

---

## Pirmasis paleidimas

Pirmą kartą paleidus programą:

1. Sukuriamas katalogas:

```text
data/
```

2. Sugeneruojamas AES raktas:

```text
data/aes.key
```

3. Sukuriamas slaptažodžių failas:

```text
data/passwords.csv
```

---

## Naujo slaptažodžio pridėjimas

1. Įveskite:

* Pavadinimą
* Slaptažodį
* URL arba programos pavadinimą
* Pastabas

2. Paspauskite:

```text
Pridėti
```

Slaptažodis prieš išsaugojimą automatiškai užšifruojamas AES algoritmu.

---

## Slaptažodžio paieška

1. Laukelyje „Pavadinimas“ įveskite ieškomą įrašą.
2. Paspauskite:

```text
Ieškoti
```

Programa parodys:

* pavadinimą;
* URL;
* pastabas.

Slaptažodis nebus rodomas automatiškai.

---

## Slaptažodžio peržiūra

1. Lentelėje pasirinkite įrašą.
2. Paspauskite:

```text
Rodyti slaptažodį
```

Programa iššifruos slaptažodį ir parodys jį atskirame lange.

---

## Slaptažodžio kopijavimas

1. Lentelėje pasirinkite įrašą.
2. Paspauskite:

```text
Kopijuoti
```

Slaptažodis bus nukopijuotas į operacinės sistemos iškarpinę (Clipboard).

---

## Slaptažodžio atnaujinimas

1. Pasirinkite įrašą.
2. Pakeiskite reikšmes.
3. Paspauskite:

```text
Atnaujinti
```

Naujas slaptažodis bus užšifruotas iš naujo.

---

## Slaptažodžio ištrynimas

1. Pasirinkite įrašą.
2. Paspauskite:

```text
Ištrinti
```

Įrašas bus pašalintas iš duomenų failo.

---

## Failų apsauga

Programos veikimo metu naudojamas failas:

```text
passwords.csv
```

Uždarius programą:

```text
passwords.csv
```

užšifruojamas į:

```text
passwords.enc
```

o originalus CSV failas pašalinamas.

Kitą kartą paleidus programą:

```text
passwords.enc
```

automatiškai iššifruojamas atgal į:

```text
passwords.csv
```

---

## Duomenų formatas

CSV failas:

```csv
title,encryptedPassword,url,notes
GitHub,U29tZUVuY3J5cHRlZERhdGE=,https://github.com,Asmeninė paskyra
```

---

## Saugumo sprendimai

### AES šifravimas

Programa naudoja AES algoritmą:

```text
Advanced Encryption Standard
```

AES yra simetrinio rakto blokinis šifravimo algoritmas, naudojamas failų ir duomenų apsaugai.

### Base64 kodavimas

Base64 naudojamas užšifruotiems duomenims saugoti tekstiniuose failuose.

Svarbu:

```text
Base64 nėra šifravimas.
Base64 yra duomenų kodavimo metodas.
```

---

## Autorius

Kęstutis Skrebė

Vilniaus kolegija

Software Engineering
Informacijos saugumas
