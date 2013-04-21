<?xml version="1.0" encoding="UTF-8"?>

<!--
    Extracts metadata from MODS for label of Fedora Object as plain text.

    Expects input parameter MODEL.
-->

<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:mods="http://www.loc.gov/mods/v3"
                >
    <xsl:output method="text"/>

    <!--MODEL = model:page|... -->
    <xsl:param name="MODEL" />

    <xsl:template match="/">
        <xsl:choose>
            <xsl:when test="$MODEL = 'model:page' or $MODEL = 'model:periodicalitem' or $MODEL = 'model:periodicalvolume'">
                <xsl:variable name="pagePart" select="//mods:mods[1]/mods:part[1]" />
                <!--<xsl:apply-templates select="//mods:mods[1]/mods:part[1]" mode="page" />-->
                <xsl:apply-templates select="//mods:mods[1]/mods:part[1]" />
            </xsl:when>
            <xsl:otherwise>
                <xsl:apply-templates select="//mods:mods[1]/*"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template match="mods:titleInfo[1]">
        <xsl:variable name="title">
            <xsl:value-of select="mods:nonSort"/>
            <xsl:if test="string(mods:nonSort)">
                <xsl:text> </xsl:text>
            </xsl:if>
            <xsl:value-of select="mods:title"/>
            <xsl:if test="string(mods:subTitle)">
                <xsl:text>: </xsl:text>
                <xsl:value-of select="mods:subTitle"/>
            </xsl:if>
            <xsl:if test="string(mods:partNumber)">
                <xsl:text>. </xsl:text>
                <xsl:value-of select="mods:partNumber"/>
            </xsl:if>
            <xsl:if test="string(mods:partName)">
                <xsl:text>. </xsl:text>
                <xsl:value-of select="mods:partName"/>
            </xsl:if>
        </xsl:variable>

        <xsl:if test="string($title)">
            <xsl:value-of select="$title"/>
        </xsl:if>
    </xsl:template>

    <!-- title for: page; syntax: pageNumber, pageType -->
    <xsl:template match="mods:detail[@type='pageNumber']/mods:number[1]">
        <xsl:variable name="pageNumber" select="."/>
        <xsl:variable name="pageType" select="../../@type"/>
        <xsl:value-of select="$pageNumber"/>
        <xsl:if test="string($pageType) and string($pageType) != 'NormalPage'">
            <xsl:text>, </xsl:text>
            <xsl:value-of select="$pageType"/>
        </xsl:if>
    </xsl:template>

    <!-- title for: periodicalvolume; syntax: volumeDate, volumeNumber -->
    <xsl:template match="mods:detail[@type='volume']/mods:number[1]">
        <xsl:value-of select="../../mods:date"/>
        <xsl:text>, </xsl:text>
        <xsl:value-of select="."/>
    </xsl:template>

    <!-- title for: periodicalitem; syntax: itemNumber -->
    <xsl:template match="mods:detail[@type='issue']/mods:number[1]">
        <xsl:value-of select="."/>
    </xsl:template>

    <!-- title for: monographunit -->
    <xsl:template match="mods:part[@type='Volume']/mods:detail/mods:number[1]">
        <xsl:value-of select="."/>
    </xsl:template>

    <xsl:template match="text()|@*" />

</xsl:stylesheet>
