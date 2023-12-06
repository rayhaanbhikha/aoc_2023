import kotlin.io.path.Path
import kotlin.math.abs



fun main() {
   println(input.getMinSeedLocation())
}

class Input(
    rawSeeds: String,
    rawSeedToSoilMap: String,
    rawSoilToFertilizerMap: String,
    rawFertilizerToWaterMap: String,
    rawWaterToLight: String,
    rawLightToTemperature: String,
    rawTemperatureToHumidity: String,
    rawHumidityToLocation: String,
) {
    private val seeds = parseNumRow(rawSeeds)
    private val seedToSoilMap = parseMap(rawSeedToSoilMap)
    private val soilToFertilizerMap = parseMap(rawSoilToFertilizerMap)
    private val fertilizerToWaterMap = parseMap(rawFertilizerToWaterMap)
    private val waterToLight = parseMap(rawWaterToLight)
    private val lightToTemperature = parseMap(rawLightToTemperature)
    private val temperatureToHumidity = parseMap(rawTemperatureToHumidity)
    private val humidityToLocation = parseMap(rawHumidityToLocation)

//    init {
//        val l = mutableSetOf<Long>()
//        for (i in seeds.indices step 2) {
//            val start = seeds[i]
//            val end = seeds[i] + seeds[i + 1] - 1
//            for (j in start..end) {
//                l.add(j)
//            }
//        }
//        seeds2 = l.toSet()
//    }

//    fun getMinSeedLocationPart2() {
//        val memo = mutableMapOf<Long, Long>()
//        val res = seeds2.minOfOrNull { seed ->
//            val res = memo[seed] ?: getLocation(seed)
//            memo[seed] = res
//            res
//        }
//        println(res)
//    }

    fun getMinSeedLocation() = input.seeds.onEach {
        println("$it -> ${getLocation(it)}")
    }.minOfOrNull { getLocation(it) }

    private fun getLocation(startNum: Long): Long {
        return seedToSoilMap.getNumber(startNum).let {
            soilToFertilizerMap.getNumber(it)
        }.let {
            fertilizerToWaterMap.getNumber(it)
        }.let {
            waterToLight.getNumber(it)
        }.let {
            lightToTemperature.getNumber(it)
        }.let {
            temperatureToHumidity.getNumber(it)
        }.let {
            humidityToLocation.getNumber(it)
        }
    }

    private fun parseNumRow(row: String) = row.split(" ").mapNotNull { it.toLongOrNull() }

    // map containing source start range, destination start range and the range length.
    private fun parseMap(rawMap: String): List<Range> =
        rawMap.lines().map {
            parseNumRow(it)
        }.map { Range(it[0], it[1], it[2]) }
}

data class Range(val destinationStart: Long, val sourceStart: Long, val rangeLength: Long) {
    val sourceStartRange = LongRange(sourceStart, sourceStart + rangeLength)
    fun getNumber(startNum: Long): Long {
        if (!sourceStartRange.contains(startNum)) return startNum
        return abs(startNum.minus(sourceStartRange.first)) + destinationStart
    }
}

fun List<Range>.getNumber(startNum: Long): Long {
    return this.map { range -> range.getNumber(startNum) }.firstOrNull { mappedNum -> mappedNum != startNum }
        ?: startNum
}

val exampleInput = Input(
    rawSeeds = "79 14 55 13",
    rawSeedToSoilMap = """
            50 98 2
            52 50 48
        """.trimIndent(),
    rawSoilToFertilizerMap = """
            0 15 37
            37 52 2
            39 0 15
        """.trimIndent(),
    rawFertilizerToWaterMap = """
            49 53 8
            0 11 42
            42 0 7
            57 7 4
        """.trimIndent(),
    rawWaterToLight = """
            88 18 7
            18 25 70
        """.trimIndent(),
    rawLightToTemperature = """
            45 77 23
            81 45 19
            68 64 13
        """.trimIndent(),
    rawTemperatureToHumidity = """
            0 69 1
            1 0 69
        """.trimIndent(),
    rawHumidityToLocation = """
            60 56 37
            56 93 4
        """.trimIndent()
)

val input = Input(
    rawSeeds = "2041142901 113138307 302673608 467797997 1787644422 208119536 143576771 99841043 4088720102 111819874 946418697 13450451 3459931852 262303791 2913410855 533641609 2178733435 26814354 1058342395 175406592",
    rawSeedToSoilMap = """
1270068015 1235603193 242614277
13415696 1478217470 21049126
825250550 1160341941 75261252
3786189027 1971702238 242038712
3191605340 3433644052 172752250
2389904665 3088515862 345128190
0 1499266596 13415696
1197451933 0 72616082
2139929050 1721726623 249975615
900511802 222541761 147014452
1047526254 72616082 149925679
34464822 980591812 179750129
2735032855 2631943377 456572485
3364357590 3606396302 421831437
214214951 369556213 611035599
1721726623 2213740950 418202427
        """.trimIndent(),
    rawSoilToFertilizerMap = """
226793587 358613369 356867344
0 1838890301 226793587
2741010192 0 358613369
2257843811 715480713 173982825
3099623561 1264222741 3082010
1810570233 2912833547 326150077
4038242924 3815312886 256724372
2431826636 3268919687 279247493
866869902 1671637223 167253078
3102705571 889463538 374759203
1615333950 2646894858 125679350
2136720310 1550513722 121123501
3477464774 2772574208 140259339
1034122980 2065683888 581210970
2711074129 3238983624 29936063
583660931 1267304751 283208971
1741013300 3548167180 69556933
3815312886 4072037258 222930038
        """.trimIndent(),
    rawFertilizerToWaterMap = """
2197389106 1911800263 305927673
3117278994 244526473 20512291
2957263700 4069704360 136102838
1536592951 2749121858 245216219
3093366538 840656765 23912456
815267737 2718368501 30753357
3137791285 3392529258 82147206
3770129899 390297301 198889495
2168252150 2217727936 29136956
770420674 2367771416 44847063
3304122356 4205807198 44047643
94626393 318562852 71734449
2518697514 2262245627 10899396
1967704281 265038764 53524088
846021094 2994338077 264934624
1215514867 3881882833 187821527
1781809170 2556752263 100818318
1882627488 1690877042 85076793
208796777 631622731 209034034
2583608714 3508227847 39876494
519341481 1587040614 19652563
599791964 104559149 139967324
3430004623 1246915338 340125276
1110955718 0 104559149
2623485208 3548104341 333778492
1403336394 3259272701 133256557
0 2273145023 94626393
2529596910 1857788459 54011804
166360842 589186796 42435935
3219938491 1606693177 84183865
2165362153 3505337850 2889997
2021228369 2412618479 72557496
538994044 2657570581 60797920
739759288 3474676464 30661386
417830811 1154801218 92114120
509944931 864569221 9396550
2093785865 2485175975 71576288
3969019394 873965771 280835447
2503316779 2246864892 15380735
3348169999 1775953835 81834624
        """.trimIndent(),
    rawWaterToLight = """
3059617387 1101868951 93963271
2772853640 2098642805 169363292
193287974 683814429 1482323
3780234682 1734000399 364642406
194770297 193287974 490526455
3550500423 902417243 199451708
3466829263 2268006097 83671160
3379471616 2976813583 87357647
2283311570 2369871058 489542070
3768145932 1195832222 12088750
1202605712 3064171230 1080705858
2942216932 2859413128 107440729
3749952131 2351677257 18193801
3049657661 2966853857 9959726
902417243 1433811930 300188469
3153580658 1207920972 225890958
        """.trimIndent(),
    rawLightToTemperature = """
1088722200 1345179841 10000894
4202170199 3029124889 92797097
3016749137 3130683307 53232372
534766220 331633133 9297796
190275035 286706851 44926282
3069981509 1355180735 96127340
1579189291 3913052943 379484702
3918942962 3793498681 21754451
262413801 377451173 88614642
2452226090 2443004441 143726844
1958673993 2586731285 188604820
2595952934 3815253132 97799811
882433672 754981589 206288528
608195463 3121921986 8761321
393888178 145828809 140878042
3166108849 1530740045 97835767
351028443 139489318 6339491
3410232629 961270117 222147493
2449796439 4292537645 2429651
2401067597 1890048598 48728842
3263944616 2376148398 66856043
3330800659 1451308075 79431970
3632380122 2019388074 205952206
1548873457 1323582243 21597598
2693752745 2225340280 142089882
357367934 340930929 36520244
0 493278299 50785717
768194739 645497224 68742488
2876584504 1183417610 140164633
235201317 466065815 27212484
1570471055 2367430162 8718236
2147278813 2775336105 253788784
874238988 3335153634 8194684
2835842627 714239712 40741877
3838332328 1938777440 80610634
50785717 0 139489318
616956784 3183915679 151237955
836937227 608195463 37301761
1098723094 3343348318 450150363
3940697413 1628575812 261472786
        """.trimIndent(),
    rawTemperatureToHumidity = """
3865679795 3411240257 137388137
0 569718370 128927628
2284256983 1093255418 349419361
2189606444 2745948834 16886013
3861728218 3796217004 3951577
700036088 2134161201 67667679
2639129496 1078899666 14355752
4003067932 4146768977 27598493
569558723 2311178479 130477365
4125126694 3887925067 11822916
1884679865 1442674779 129657290
2633676344 1572332069 5453152
862461784 1577785221 531688422
4030666425 4120171499 26597478
195554894 115268399 39538402
235093296 235252943 334465427
2132287170 698645998 57319274
4158735055 3550090086 136232241
2206492457 157488417 77764526
3562496130 3899747983 220423516
2653485248 2201828880 109349599
2014337155 154806801 2681616
1580787383 2536413861 97415145
3453031511 3880534101 7390966
1394150206 2633829006 45492562
4057263903 3686322327 7821024
4157273363 3548628394 1461692
3460422477 3694143351 102073653
4136949610 3800168581 20323753
1678202528 872422329 206477337
1556099825 2109473643 24687558
128927628 2679321568 66627266
2017018771 0 115268399
4065084927 3820492334 60041767
3411240257 4253176042 41791254
767703767 2441655844 94758017
1439642768 755965272 116457057
3782919646 4174367470 78808572
        """.trimIndent(),
    rawHumidityToLocation = """
347042062 4204488573 2962316
877642375 2878291222 23518224
3638916554 3484768713 5466055
2890676240 3280663590 83564486
201731623 3228655138 52008452
1692198890 1946017811 25211210
1717410100 1270737209 92827656
3598929376 3630765534 39987178
2226479211 1578430526 262844314
687169155 3670752712 117494406
3884728324 1515480569 62949957
3494186405 1841274840 104742971
2753482998 2630924017 120642845
572458731 2235388494 114710424
3947678281 3490234768 140530766
3433348031 3167816764 60838374
350004378 2406580589 222454353
804663561 1442501755 72978814
4132461733 2901809446 35781203
4088209047 2937590649 38937925
1441554453 2350098918 56481671
3644382609 201731623 240345715
2489323525 1971229021 264159473
2974240726 3086380958 81435806
4127146972 3364228076 5314761
901160599 4207450889 87516407
988677006 442077338 350976352
1498036124 3369542837 115225876
1810237756 3788247118 416241455
1613262000 1363564865 78936890
253740075 2993078971 93301987
3055676532 894954785 375782424
1339653358 793053690 101901095
2874125843 2976528574 16550397
3431458956 2629034942 1889075
4168242936 2751566862 126724360
        """.trimIndent()
)

class Day05 : Day {
    override fun part1() = input.getMinSeedLocation()

    override fun part2(): Any {
        return 0
    }

}