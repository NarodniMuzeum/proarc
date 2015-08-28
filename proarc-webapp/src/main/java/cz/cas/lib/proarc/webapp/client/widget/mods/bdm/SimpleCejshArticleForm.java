/*
 * Copyright (C) 2014 Jan Pokorsky
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package cz.cas.lib.proarc.webapp.client.widget.mods.bdm;

import cz.cas.lib.proarc.webapp.client.widget.mods.NdkForms;
import cz.cas.lib.proarc.webapp.shared.form.Field;
import cz.cas.lib.proarc.webapp.shared.form.FieldBuilder;
import cz.cas.lib.proarc.webapp.shared.form.Form;
import java.util.List;

/**
 * The simplified CEJSH form. Issue 321.
 *
 * @author Jan Pokorsky
 */
public final class SimpleCejshArticleForm {

    public Form build() {
        Form f = new Form();

        // CEJSH, issue 234
        f.getFields().add(new FieldBuilder("reviewed").setTitle("Status recenzování - M").setMaxOccurrences(1)
                .setType(Field.RADIOGROUP).setRequired(true)
                .addMapValue("true", "recenzovaný článek")
                .addMapValue("false", "nerecenzovaný článek")
                .createField());

        Field mods = new FieldBuilder("mods").setMaxOccurrences(1).createField();
        f.getFields().add(mods);
        List<Field> modsFields = mods.getFields();

        modsFields.add(genre());
        modsFields.add(language());
//        modsFields.add(part());
        modsFields.add(titleInfo(f.getItemWidth()));
        modsFields.add(name());
        modsFields.add(abstracts());
        modsFields.add(subject());
        modsFields.add(note());
        modsFields.add(identifier());
        modsFields.add(recordInfo());
        // XXX Element pro recenzovaný dokument relatedItem/*
        modsFields.add(new FieldBuilder("relatedItem").setTitle("Recenze na - R").setMaxOccurrences(10)
                .addField(part())
                .addField(relatedTitleInfo(f.getItemWidth()))
                .addField(relatedName())
                .addField(relatedOriginInfo())
                .createField());
        modsFields.add(new FieldBuilder("version").setType(Field.TEXT).setHidden(true).setMaxOccurrences(1).createField());

        return f;
    }

    private Field titleInfo(String width) {
        // titleInfo, titleInfoDefinition
        return new FieldBuilder("titleInfo").setTitle("Názvové údaje - M").setMaxOccurrences(10)
                .setHint("Názvová informace vnitřní části.")
                // titleInfo@type, enum
                .addField(new FieldBuilder("type").setTitle("Typ - MA").setMaxOccurrences(1).setType(Field.SELECT)
                    .setHint("Hlavní název bez type.<dl>Hodnoty:"
                        + "<dt>abbreviated</dt><dd>zkrácený název</dd>"
                        + "<dt>alternative</dt><dd>alternativní název</dd>"
                        + "<dt>translated</dt><dd>přeložený název</dd>"
                        + "<dt>uniform</dt><dd>stejný/jednotný název</dd>"
                        + "</dl>")
                    .addMapValue("abbreviated", "Abbreviated")
                    .addMapValue("alternative", "Alternative")
                    .addMapValue("translated", "Translated")
                    .addMapValue("uniform", "Uniform")
                .createField()) // type
                // nonSort, type="stringPlusLanguage"
                .addField(new FieldBuilder("nonSort").setMaxOccurrences(1)
                    .addField(new FieldBuilder("value").setTitle("Člen, jímž začíná název - MA").setMaxOccurrences(1).setType(Field.TEXT)
                    .createField()) // value
                    // stringPlusLanguage: @lang, @xmlLang, @script, @transliteration
                .createField()) // nonSort
                // title, type="stringPlusLanguage"
                .addField(new FieldBuilder("title").setMaxOccurrences(1)
                    // lang, issue 235
                    .addField(new FieldBuilder("lang").setTitle("Jazyk názvu - O").setMaxOccurrences(1).setType(Field.COMBO).setRequired(false)
                        .setHint("Kód jazyka podle  ISO 639-2/b.")
                        .setOptionDataSource(new FieldBuilder("ndk.mods.languageTerms").setWidth("300")
                                .addField(new FieldBuilder("title").setTitle("Name").createField())
                                .addField(new FieldBuilder("value").setTitle("Language").createField())
                            .createField(),
                            "value")
                    .createField()) // title@lang
                    .addField(new FieldBuilder("value").setTitle("Název - M").setMaxOccurrences(1).setType(Field.COMBO).setRequired(true).setWidth(width)
                        .setHint("Vlastní název článku."
                            + "<p>Pokud není titul, nutno vyplnit hodnotu „untitled“")
                        .addMapValue("untitled", "untitled")
                    .createField()) // title/value
                    // lang, String
                    // xmlLang, lang
                    // script, String
                    // transliteration, String
                .createField()) // title
                // subTitle, type="stringPlusLanguage"
                .addField(new FieldBuilder("subTitle").setMaxOccurrences(1)
                    .addField(new FieldBuilder("value").setTitle("Podnázev - MA").setMaxOccurrences(1).setType(Field.TEXT)
                        .setHint("Podnázev článku. Za podnázev lze považovat i perex.")
                    .createField()) // value
                    // stringPlusLanguage: @lang, @xmlLang, @script, @transliteration
                .createField()) // subTitle
                // partNumber, type="stringPlusLanguage"
                .addField(new FieldBuilder("partNumber").setMaxOccurrences(1)
                    // stringPlusLanguage: @lang, @xmlLang, @script, @transliteration
                    .addField(new FieldBuilder("value").setTitle("Číslo části - RA").setMaxOccurrences(1).setType(Field.TEXT)
                        .setHint("Číslo článku. Např. článek na pokračování.")
                    .createField()) // value
                .createField()) // partNumber
                // partName, type="stringPlusLanguage"
                .addField(new FieldBuilder("partName").setMaxOccurrences(1)
                    // stringPlusLanguage: @lang, @xmlLang, @script, @transliteration
                    .addField(new FieldBuilder("value").setTitle("Název části - RA").setMaxOccurrences(1).setType(Field.TEXT)
                        .setHint("Název pokračování článku.")
                    .createField()) // value
                .createField()) // partName
                // titleInfo@attributes: otherType, supplied, altRepGroup, altFormatAttributeGroup, nameTitleGroup, usage, ID, authorityAttributeGroup, xlink:simpleLink, languageAttributeGroup, displayLabel
            .createField(); // titleInfo
    }

    private Field relatedTitleInfo(String width) {
        // titleInfo, titleInfoDefinition
        return new FieldBuilder("titleInfo").setMaxOccurrences(1)
                // title, type="stringPlusLanguage"
                .addField(new FieldBuilder("title").setMaxOccurrences(1)
                    .addField(new FieldBuilder("value").setMaxOccurrences(1).setType(Field.TEXT)
                        .setTitle("Název recenzovaného díla")
                        .setRequired(false).setWidth(width)
                    .createField()) // title/value
                .createField()) // title
            .createField(); // titleInfo
    }

    private Field name() {
        // name, nameDefinition
        return new FieldBuilder("name").setMaxOccurrences(10).setTitle("Údaje o autorech - MA")
                .setHint("Údaje o odpovědnosti za článek.")
                // @ID, @authorityAttributeGroup, @xlinkSimpleLink, @languageAttributeGroup, @displayLabel, @altRepGroup, @nameTitleGroup
                // @type(personal, corporate, conference, family)
                .addField(new FieldBuilder("type").setTitle("Type - R").setMaxOccurrences(1).setType(Field.TEXT)
                    .setDefaultValue("personal")
                    .setHidden(true)
//                    .setHint("<dl>"
//                        + "<dt>personal</dt><dd>celé jméno osoby</dd>"
//                        + "<dt>corporate</dt><dd>název společnosti, instituce nebo organizace</dd>"
//                        + "<dt>conference</dt><dd>název konference nebo související typ setkání</dd>"
//                        + "<dt>family</dt><dd>rodina/rod</dd>"
//                        + "</dl>")
//                    .addMapValue("personal", "personal")
//                    .addMapValue("corporate", "corporate")
//                    .addMapValue("conference", "conference")
//                    .addMapValue("family", "family")
                .createField()) // @type
                // @usage(fixed="primary")
//                .addField(new FieldBuilder("usage").setTitle("Usage - O").setMaxOccurrences(1).setType(Field.SELECT).setDefaultValue("")
//                    .setHint("Hodnota “primary” pro označení primární autority.")
//                    .addMapValue("", "")
//                    .addMapValue("primary", "primary")
//                .createField()) // usage
                // namePart, namePartDefinition extends stringPlusLanguage
                .addField(new FieldBuilder("namePart").setTitle("Name Parts - MA").setMaxOccurrences(5)
                    // @type(date, family, given, termsOfAddress)
                    .addField(new FieldBuilder("type").setTitle("Type - MA").setMaxOccurrences(1).setType(Field.SELECT)
                        .setHint("<dl>"
//                            + "<dt>date</dt><dd>RA - datum</dd>"
                            + "<dt>family</dt><dd>MA -příjmení </dd>"
                            + "<dt>given</dt><dd>MA - jméno/křestní jméno</dd>"
                            + "<dt>termsOfAddress</dt><dd>RA - tituly a jiná slova nebo čísla související se jménem</dd>"
                            + "</dl>")
//                        .addMapValue("date", "date")
                        .addMapValue("family", "family")
                        .addMapValue("given", "given")
                        .addMapValue("termsOfAddress", "termsOfAddress")
                    .createField()) // @type
                    // stringPlusLanguage: @lang, @xmlLang, @script, @transliteration
                    .addField(new FieldBuilder("value").setTitle("Name Part - MA").setMaxOccurrences(1).setType(Field.TEXT)
                        .setHint("Údaje o křestním jméně, příjmení apod."
                            + "<p>Nutno vyjádřit pro křestní jméno i příjmení."
                            + "<p>Pokud nelze rozlišit křestní jméno a příjmení,"
                            + " nepoužije se type a jméno se zaznamená"
                            + " v podobě jaké je do jednoho elementu &lt;namePart>")
                    .createField()) // value
                .createField()) // namePart
                // displayForm
                // etal
                // affiliation
                // role, roleDefinition
                // description
            .createField(); // name
    }

    private Field relatedName() {
        // name, nameDefinition
        return new FieldBuilder("name").setMaxOccurrences(10).setTitle("Autor recenzovaného díla")
//                .setHint("Údaje o odpovědnosti za článek.")
                // @ID, @authorityAttributeGroup, @xlinkSimpleLink, @languageAttributeGroup, @displayLabel, @altRepGroup, @nameTitleGroup
                // @type(personal, corporate, conference, family)
                .addField(new FieldBuilder("type").setTitle("Type - R").setMaxOccurrences(1).setType(Field.TEXT)
                    .setDefaultValue("personal")
                    .setHidden(true)
//                    .setHint("<dl>"
//                        + "<dt>personal</dt><dd>celé jméno osoby</dd>"
//                        + "<dt>corporate</dt><dd>název společnosti, instituce nebo organizace</dd>"
//                        + "<dt>conference</dt><dd>název konference nebo související typ setkání</dd>"
//                        + "<dt>family</dt><dd>rodina/rod</dd>"
//                        + "</dl>")
//                    .addMapValue("personal", "personal")
//                    .addMapValue("corporate", "corporate")
//                    .addMapValue("conference", "conference")
//                    .addMapValue("family", "family")
                .createField()) // @type
                // @usage(fixed="primary")
//                .addField(new FieldBuilder("usage").setTitle("Usage - O").setMaxOccurrences(1).setType(Field.SELECT).setDefaultValue("")
//                    .setHint("Hodnota “primary” pro označení primární autority.")
//                    .addMapValue("", "")
//                    .addMapValue("primary", "primary")
//                .createField()) // usage
                // namePart, namePartDefinition extends stringPlusLanguage
                .addField(new FieldBuilder("namePart").setTitle("Name Parts - MA").setMaxOccurrences(5)
                    // @type(date, family, given, termsOfAddress)
                    .addField(new FieldBuilder("type").setTitle("Type - MA").setMaxOccurrences(1).setType(Field.SELECT)
                        .setHint("<dl>"
//                            + "<dt>date</dt><dd>RA - datum</dd>"
                            + "<dt>family</dt><dd>MA -příjmení </dd>"
                            + "<dt>given</dt><dd>MA - jméno/křestní jméno</dd>"
                            + "<dt>termsOfAddress</dt><dd>RA - tituly a jiná slova nebo čísla související se jménem</dd>"
                            + "</dl>")
//                        .addMapValue("date", "date")
                        .addMapValue("family", "family")
                        .addMapValue("given", "given")
                        .addMapValue("termsOfAddress", "termsOfAddress")
                    .createField()) // @type
                    // stringPlusLanguage: @lang, @xmlLang, @script, @transliteration
                    .addField(new FieldBuilder("value").setTitle("Name Part - MA").setMaxOccurrences(1).setType(Field.TEXT)
                        .setHint("Údaje o křestním jméně, příjmení apod."
                            + "<p>Nutno vyjádřit pro křestní jméno i příjmení."
                            + "<p>Pokud nelze rozlišit křestní jméno a příjmení,"
                            + " nepoužije se type a jméno se zaznamená"
                            + " v podobě jaké je do jednoho elementu &lt;namePart>")
                    .createField()) // value
                .createField()) // namePart
                // displayForm
                // etal
                // affiliation
                // role, roleDefinition
                // description
            .createField(); // name
    }

    private Field genre() {
        // genre, genreDefinition extends stringPlusLanguagePlusAuthority extends stringPlusLanguage
        return new FieldBuilder("genre").setMaxOccurrences(1)
                // genreDefinition@attributes: type, displayLabel, altRepGroup, usage
                .addField(new FieldBuilder("type").setTitle("Typ obsahu - R").setMaxOccurrences(1).setType(Field.COMBO).setWidth("200")
                    .addMapValue("main article", "hlavní článek")
                    .addMapValue("news", "zpráva")
                    .addMapValue("table of content", "obsah")
                    .addMapValue("advertisement", "reklama")
                    .addMapValue("abstract", "abstrakt")
                    .addMapValue("introduction", "úvod")
                    .addMapValue("review", "recenze")
                    .addMapValue("dedication", "věnování")
                    .addMapValue("bibliography", "bibliografie")
                    .addMapValue("editorsNote", "úvodník")
                    .addMapValue("preface", "předmluva")
                    .addMapValue("index", "rejstřík")
                    .addMapValue("unspecified", "nespecifikován")
                .createField()) // @type
                // stringPlusLanguagePlusAuthority: authorityAttributeGroup
                // stringPlusLanguage: @lang, @xmlLang, @script, @transliteration
                .addField(new FieldBuilder("value").setTitle("Genre - M").setMaxOccurrences(1).setType(Field.TEXT)
                    .setHidden(true)
                .createField()) // value
        .createField(); // genre
    }

    private Field language() {
        // language, languageDefinition
        return new FieldBuilder("language").setMaxOccurrences(1)
                .setHint("Údaje o jazyce dokumentu; v případě vícenásobného výskytu nutno element &lt;language> opakovat")
                // @objectPart, @displayLabel, @altRepGroup, @usage
                // languageAttributeGroup: @lang, @xmlLang, @script, @transliteration
                // languageTerm, languageTermDefinition
                .addField(new FieldBuilder("languageTerm").setMaxOccurrences(1)
                    // stringPlusLanguage: @lang, @xmlLang, @script, @transliteration
                    // @authorityURI, @valueURI
                    // @authority, enum
                    .addField(new FieldBuilder("authority").setTitle("Authority - M").setMaxOccurrences(1).setType(Field.TEXT)
                        .setHidden(true)
                    .createField()) // authority
                    // type, codeOrText('code', 'text')
                    .addField(new FieldBuilder("type").setTitle("Type - M").setMaxOccurrences(1).setType(Field.TEXT)
                        .setHidden(true)
                    .createField()) // type
                    .addField(NdkForms.createLangTermValue().setTitle("Jazyk článku - M")
                    .createField()) // value
                .createField()) // languageTerm
                // scriptTerm
        .createField(); // language
    }

    private Field physicalDescription() {
        // physicalDescription, physicalDescriptionDefinition
        return new FieldBuilder("physicalDescription").setTitle("Physical Description - R").setMaxOccurrences(10)
                .setHint("Obsahuje údaje o fyzickém popisu článku.")
                // form, formDefinition extends stringPlusLanguagePlusAuthority
                .addField(new FieldBuilder("form").setTitle("Form - R").setMaxOccurrences(1)
                    // stringPlusLanguagePlusAuthority: authorityAttributeGroup: @authority, @authorityURI, @valueURI
                    // stringPlusLanguage: @lang, @xmlLang, @script, @transliteration
                    // @type, string
                    // XXX autofill "marcform"
                    .addField(new FieldBuilder("authority").setTitle("Authority - R").setMaxOccurrences(1).setType(Field.COMBO).setDefaultValue("marcform")
                        .addMapValue("marcform", "marcform")
                        .addMapValue("gmd", "gmd")
                    .createField()) // authority
                    .addField(new FieldBuilder("value").setTitle("Form - R").setMaxOccurrences(1).setType(Field.COMBO)
                        .setHint("Údaje o fyzické podobě dokumentu, např. print, electronic, microfilm apod."
                            + "<p>Odpovídá hodnotě v poli 008/23")
                        .addMapValue("braille", "braille")
                        .addMapValue("electronic", "electronic")
                        .addMapValue("large print", "large print")
                        .addMapValue("microfilm", "microfilm")
                        .addMapValue("microfiche", "microfiche")
                        .addMapValue("print", "print")
                    .createField()) // value
                .createField()) // form
                // reformattingQuality
                // internetMediaType
                // digitalOrigin
                // extent, stringPlusLanguagePlusSupplied
                // note, physicalDescriptionNote extends stringPlusLanguage
        .createField(); // physicalDescription
    }

    private Field abstracts() {
        // abstract, abstractDefinition extends stringPlusLanguage
        return new FieldBuilder("abstract").setTitle("Abstrakt - R").setMaxOccurrences(10)
                // stringPlusLanguage: @lang, @xmlLang, @script, @transliteration
                // @displayLabel, @type, @xlink:simpleLink, @shareable, @altRepGroup
                // altFormatAttributeGroup: @altFormat, @contentType
                .addField(new FieldBuilder("lang").setTitle("Jazyk abstraktu").setMaxOccurrences(1).setType(Field.COMBO).setRequired(false)
                    .setHint("Kód jazyka podle  ISO 639-2/b.")
                    .setOptionDataSource(new FieldBuilder("ndk.mods.languageTerms").setWidth("300")
                            .addField(new FieldBuilder("title").setTitle("Name").createField())
                            .addField(new FieldBuilder("value").setTitle("Language").createField())
                        .createField(),
                        "value")
                .createField()) // @lang
                .addField(new FieldBuilder("value").setMaxOccurrences(1).setType(Field.TEXTAREA)
                    .setHint("Shrnutí obsahu článku.")
                .createField()) // value
        .createField(); // abstract
    }

    private Field note() {
        // note, noteDefinition extends stringPlusLanguage
        return new FieldBuilder("note").setTitle("Poznámka - R").setMaxOccurrences(10)
                // stringPlusLanguage: @lang, @xmlLang, @script, @transliteration
                // @displayLabel, @type, @typeURI, @xlink:simpleLink, @ID, @altRepGroup
                .addField(new FieldBuilder("value").setMaxOccurrences(1).setType(Field.TEXTAREA)
                    .setHint("Obecná poznámka k vnitřní části. Do poznámky by se"
                        + " měla dávat šifra autora vnitřní části, která se vyskytuje pod vnitřní částí.")
                .createField()) // value
        .createField(); // note
    }

    private Field subject() {
        // subject, subjectDefinition
        return new FieldBuilder("subject").setTitle("Klíčová slova - R").setMaxOccurrences(10)
                .setHint("Údaje o věcném třídění.")
                // @ID, @authorityAttributeGroup, @languageAttributeGroup, @xlink:simpleLink, @displayLabel, @altRepGroup, @usage
//                .addField(new FieldBuilder("authority").setTitle("Authority - O").setMaxOccurrences(1).setType(Field.TEXT)
//                    .addMapValue("czenas", "czenas")
//                    .addMapValue("eczenas", "eczenas")
//                .createField()) // authority

                // topic, stringPlusLanguagePlusAuthority
                .addField(new FieldBuilder("topic").setMaxOccurrences(1)
                    // stringPlusLanguagePlusAuthority: authorityAttributeGroup: @authority, @authorityURI, @valueURI
                    // stringPlusLanguage: @lang, @xmlLang, @script, @transliteration
                    // @type
                    // XXX autority.nkp.cz datasource
                    .addField(new FieldBuilder("lang").setTitle("Jazyk").setMaxOccurrences(1).setType(Field.COMBO).setRequired(false)
                        .setHint("Kód jazyka podle  ISO 639-2/b.")
                        .setOptionDataSource(new FieldBuilder("ndk.mods.languageTerms").setWidth("300")
                                .addField(new FieldBuilder("title").setTitle("Name").createField())
                                .addField(new FieldBuilder("value").setTitle("Language").createField())
                            .createField(),
                            "value")
                    .createField()) // @lang
                    .addField(new FieldBuilder("value").setTitle("Klíčové slovo").setMaxOccurrences(1).setType(Field.TEXT)
                        .setHint("Libovolný výraz specifikující nebo charakterizující obsah článku."
                            + "<p>Použít kontrolovaný slovník - např. z báze autorit AUT NK ČR (věcné téma)"
                            + " nebo obsah pole 650 záznamu MARC21.")
                    .createField()) // value
                .createField()) // topic

//                // geographic, stringPlusLanguagePlusAuthority
//                .addField(new FieldBuilder("geographic").setMaxOccurrences(1)
//                    // stringPlusLanguagePlusAuthority: authorityAttributeGroup: @authority, @authorityURI, @valueURI
//                    // stringPlusLanguage: @lang, @xmlLang, @script, @transliteration
//                    // @type
//                    // XXX autority.nkp.cz datasource
//                    .addField(new FieldBuilder("value").setTitle("Geographic - R").setMaxOccurrences(1).setType(Field.TEXT)
//                        .setHint("Geografické věcné třídění."
//                            + "<p>Použít kontrolovaný slovník - např. z báze autorit AUT NK ČR (geografický termín)"
//                            + " nebo obsah pole 651 záznamu MARC21.")
//                    .createField()) // value
//                .createField()) // geographic
//
//                // temporal, temporalDefinition extends dateDefinition extends stringPlusLanguage
//                .addField(new FieldBuilder("temporal").setMaxOccurrences(1)
//                    // authorityAttributeGroup: @authority, @authorityURI, @valueURI
//                    // stringPlusLanguage: @lang, @xmlLang, @script, @transliteration
//                    // @encoding, @qualifier, @point, @keyDate
//                    // XXX autority.nkp.cz datasource
//                    .addField(new FieldBuilder("value").setTitle("Temporal - R").setMaxOccurrences(1).setType(Field.TEXT).setWidth("200")
//                        .setHint("Chronologické věcné třídění."
//                            + "<p>Použít kontrolovaný slovník - např. z báze autorit AUT NK ČR (chronologický údaj)"
//                            + " nebo obsah pole 648 záznamu MARC21.")
//                    .createField()) // value
//                .createField()) // temporal
//
//                // titleInfo, subjectTitleInfoDefinition
//                // name, subjectNameDefinition
//                .addField(new FieldBuilder("name").setMaxOccurrences(1)
//                    // @type, enum: personal, corporate, ...
//                    // @ID, @xlink:simpleLink, displayLabel
//                    // languageAttributeGroup: @lang, @xmlLang, @script, @transliteration
//                    // authorityAttributeGroup: @authority, @authorityURI, @valueURI
//                    // namePart, namePartDefinition extends stringPlusLanguage
//                    .addField(new FieldBuilder("namePart").setMaxOccurrences(1)
//                        // stringPlusLanguage: @lang, @xmlLang, @script, @transliteration
//                        // @type, enum: date, family, given, termsOfAddress
//                        .addField(new FieldBuilder("value").setTitle("Name Part - R").setMaxOccurrences(1).setType(Field.TEXT)
//                            .setHint("Jméno použité jako věcné záhlaví."
//                                + "<p>Použít kontrolovaný slovník ‐ např. z báze autorit AUT NK ČR (jméno osobní)"
//                                + " nebo obsah pole 600 záznamu MARC21."
//                                + "<p>Celé jméno se zapíše do tohoto elementu.")
//                        .createField()) // value
//                    .createField()) // namePart
//                    // displayForm
//                    // affiliation
//                    // role
//                    // description
//                .createField()) // name

                // geographicCode
                // hierarchicalGeographic
                // cartographics
                // occupation
                // genre
        .createField(); // subject
    }

    private Field classification() {
        // classification, classificationDefinition extends stringPlusLanguagePlusAuthority
        return new FieldBuilder("classification").setTitle("Classification - RA").setMaxOccurrences(10)
                // stringPlusLanguagePlusAuthority: authorityAttributeGroup: @authority, @authorityURI, @valueURI
                // autofill "udc"
                .addField(new FieldBuilder("authority").setTitle("Authority - RA").setMaxOccurrences(1).setType(Field.TEXT).setDefaultValue("udc").createField())
                // stringPlusLanguage: @lang, @xmlLang, @script, @transliteration
                .addField(new FieldBuilder("value").setMaxOccurrences(1).setType(Field.TEXT)
                    .setHint("Klasifikační údaje věcného třídění podle Mezinárodního"
                        + " desetinného třídění.<p>Odpovídá poli 080 MARC21.")
                .createField()) // value
        .createField(); // classification
    }

    private Field identifier() {
        // identifier, identifierDefinition, [0,*]
        return new FieldBuilder("identifier").setTitle("Identifier - M").setMaxOccurrences(1)
                .setHidden(true).setType(Field.TEXT)
//                .setHint("Údaje o identifikátorech.<p>Obsahuje unikátní identifikátory"
//                    + " mezinárodní nebo lokální."
//                    + "<p>Uvádějí se i neplatné resp. zrušené identifikátory - atribut invalid=“yes“.")
//                // stringPlusLanguage@languageAttributeGroup
//                //   lang, xs:string
//                //   xml:lang
//                //   script, xs:string
//                //   transliteration, xs:string
//                //   type, xs:string
//                .addField(new FieldBuilder("type").setTitle("Type - M").setMaxOccurrences(1).setType(Field.COMBO).setRequired(true)
//                    .setHint("UUID - M - vygeneruje dodavatel"
//                            + "<br>URN:NBN - O - zápis ve tvaru urn:nbn:cz:ndk-123456 pro projekt NDK"
//                            + "<br>jiný interní identifikátor - R - type = barcode, oclc, sysno, permalink apod.")
//                    .addMapValue("barcode", "Čárový kód")
////                    .addMapValue("ccnb", "čČNB")
//                    .addMapValue("doi", "DOI")
//                    .addMapValue("hdl", "Handle")
////                    .addMapValue("isbn", "ISBN")
////                    .addMapValue("issn", "ISSN")
//                    .addMapValue("permalink", "Permalink")
//                    .addMapValue("sici", "SICI")
//                    .addMapValue("url", "URL")
//                    .addMapValue("urnnbn", "URN:NBN")
//                    .addMapValue("uuid", "UUID")
//                .createField())
//                // stringPlusLanguage/value
//                .addField(new FieldBuilder("value").setTitle("Identifier - M").setMaxOccurrences(1).setType(Field.TEXT).setRequired(true).createField())
//                // identifierDefinition
//                //   displayLabel, xs:string
//                //   typeURI, xs:anyURI
//                //   invalid, fixed="yes"
//                .addField(new FieldBuilder("invalid").setTitle("Invalid - MA").setMaxOccurrences(1).setType(Field.SELECT).setDefaultValue("")
//                    .addMapValue("", "Platný")
//                    .addMapValue("yes", "Neplatný")
//                .createField()) // invalid
//                //   altRepGroup, xs:string
        .createField(); // identifier
    }

    private Field part() {
        // part, type="partDefinition"
        return new FieldBuilder("part").setTitle("Rozsah článku - MA").setMaxOccurrences(1)
//                .setHint("Popis rozsahu.")
                // @ID, @type, @order, @displayLabel, @altRepGroup
                // @languageAttributeGroup(lang, XmlLang, script, transliteration)
                // detail, type="detailDefinition"
                // extent, type="extentDefinition"
                .addField(new FieldBuilder("extent").setMaxOccurrences(1)
                    // start, type="stringPlusLanguage"
                    .addField(new FieldBuilder("start").setMaxOccurrences(1)
                        .addField(new FieldBuilder("value").setTitle("První stránka článku - MA").setMaxOccurrences(1).setType(Field.TEXT)
                            .setHint("První stránka, na které článek začíná.")
                        .createField()) // value
                    .createField()) // start
                    // end, type="stringPlusLanguage"
                    .addField(new FieldBuilder("end").setMaxOccurrences(1)
                        .addField(new FieldBuilder("value").setTitle("Poslední stránka článku - MA").setMaxOccurrences(1).setType(Field.TEXT)
                            .setHint("Poslední stránka, na které článek končí.")
                        .createField()) // value
                    .createField()) // end
                    // total, type="xs:positiveInteger"
                    // list, type="stringPlusLanguage"
                .createField()) // extent
                // date
                // text
            .createField(); // part
    }

    private Field recordInfo() {
        // recordInfo, recordInfoDefinition
        return new FieldBuilder("recordInfo").setTitle("Record Info - M").setMaxOccurrences(1)
                    .setHidden(true).setType(Field.TEXT)
//            .setHint("Údaje o metadatovém záznamu - jeho vzniku, změnách apod.")
//                // languageAttributeGroup: @lang, @xmlLang, @script, @transliteration
//                // @displayLabel, @altRepGroup
//                // recordContentSource, stringPlusLanguagePlusAuthority
//                .addField(new FieldBuilder("recordContentSource").setTitle("Record Content Source - R").setMaxOccurrences(1)
//                    // stringPlusLanguagePlusAuthority: authorityAttributeGroup: @authority, @authorityURI, @valueURI
//                    .addField(new FieldBuilder("authority").setTitle("Authority - R").setMaxOccurrences(1).setType(Field.TEXT).setDefaultValue("marcorg").createField())
//                    .addField(new FieldBuilder("value").setMaxOccurrences(1).setType(Field.TEXT)
//                        .setHint("Kód nebo jméno instituce, která záznam vytvořila nebo změnila.")
//                    .createField()) // value
//                .createField()) // recordContentSource
//                // recordCreationDate, dateDefinition
//                .addField(new FieldBuilder("recordCreationDate").setMaxOccurrences(1)
//                    // stringPlusLanguagePlusAuthority: authorityAttributeGroup: @authority, @authorityURI, @valueURI
//                    // @encoding, @qualifier, @point, @keyDate
//                    .addField(new FieldBuilder("encoding").setMaxOccurrences(1).setHidden(true).setType(Field.TEXT).createField())
//                    .addField(new FieldBuilder("value").setTitle("Record Creation Date - M").setMaxOccurrences(1).setReadOnly(true).setType(Field.TEXT).createField())
//                .createField()) // recordCreationDate
//                // recordChangeDate, dateDefinition
//                .addField(new FieldBuilder("recordChangeDate").setMaxOccurrences(1)
//                    // stringPlusLanguagePlusAuthority: authorityAttributeGroup: @authority, @authorityURI, @valueURI
//                    // @encoding, @qualifier, @point, @keyDate
//                    .addField(new FieldBuilder("encoding").setMaxOccurrences(1).setHidden(true).setType(Field.TEXT).createField())
//                    .addField(new FieldBuilder("value").setTitle("Record Change Date - R").setMaxOccurrences(1).setReadOnly(true).setType(Field.TEXT).createField())
//                .createField()) // recordChangeDate
//                // recordIdentifier
//                // languageOfCataloging
//                // recordOrigin, extends stringPlusLanguage
//                .addField(new FieldBuilder("recordOrigin").setMaxOccurrences(1)
//                    // stringPlusLanguage: @lang, @xmlLang, @script, @transliteration
//                    .addField(new FieldBuilder("value").setTitle("Record Origin - R").setMaxOccurrences(1).setType(Field.COMBO).setWidth("200")
//                        .setHint("Údaje o vzniku záznamu.")
//                        .addMapValue("machine generated", "machine generated")
//                        .addMapValue("human prepared", "human prepared")
//                    .createField()) // value
//                .createField()) // recordOrigin
//                // descriptionStandard
        .createField(); // recordInfo
    }

    private Field relatedOriginInfo() {
        return new FieldBuilder("originInfo").setMaxOccurrences(1)
                .addField(new FieldBuilder("publisher").setMaxOccurrences(1)
                    .addField(new FieldBuilder("value").setMaxOccurrences(1).setType(Field.TEXT)
                        .setTitle("Nakladatelské údaje")
                    .createField()) // value
                .createField()) // publisher
            .createField(); // originInfo
    }
}
