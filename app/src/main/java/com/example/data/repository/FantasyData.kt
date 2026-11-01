package com.example.data.repository

import com.example.data.model.Contest
import com.example.data.model.ContestCategory
import com.example.data.model.ContestPrizeBreakup
import com.example.data.model.CricketMatch
import com.example.data.model.LiveBallEvent
import com.example.data.model.MatchStatus
import com.example.data.model.PitchReport
import com.example.data.model.Player
import com.example.data.model.PlayerRole

object FantasyData {

    val matches = listOf(
        CricketMatch(
            id = "match_ind_aus",
            tournament = "T20 World Championship • Super 8",
            matchType = "T20",
            team1 = "India",
            team1Short = "IND",
            team1Color = 0xFF1E40AF,
            team2 = "Australia",
            team2Short = "AUS",
            team2Color = 0xFFEAB308,
            venue = "Melbourne Cricket Ground, Australia",
            status = MatchStatus.LIVE,
            startTimeFormatted = "Today, 1:30 PM",
            countdownFormatted = "LIVE NOW",
            megaPrizePool = "₹2.50 Crores",
            team1Score = "184/4 (18.1 ov)",
            team2Score = "Yet to bat",
            statusSummary = "IND: 184/4 (18.1) • Kohli 72*(44), Hardik 36*(16) • Live",
            isLineupAnnounced = true,
            pitchReport = PitchReport(
                pitchType = "Batting Paradise with good carry for pacers",
                avgScore = 176,
                paceRatio = 65,
                spinRatio = 35,
                weather = "Clear Sky, 22°C, 12 km/h wind",
                tossAdvice = "Bat first team won 68% matches at this venue"
            ),
            liveEvents = listOf(
                LiveBallEvent(
                    ball = "18.1",
                    runs = "4",
                    description = "FOUR! Hardik Pandya smashes Starc through extra cover! Pure power!",
                    fantasyImpact = "+5 pts (Hardik Pandya: Boundary Bonus)",
                    isBoundary = true
                ),
                LiveBallEvent(
                    ball = "17.6",
                    runs = "6",
                    description = "SIX! Virat Kohli dances down the track and lofts Cummins into the 2nd tier!",
                    fantasyImpact = "+8 pts (Virat Kohli: Six + Milestone Bonus)",
                    isBoundary = true
                ),
                LiveBallEvent(
                    ball = "17.3",
                    runs = "W",
                    description = "WICKET! Suryakumar Yadav c Maxwell b Cummins 48(27). Great running catch!",
                    fantasyImpact = "+25 pts to Cummins • +8 pts catch to Maxwell",
                    isWicket = true
                ),
                LiveBallEvent(
                    ball = "17.1",
                    runs = "2",
                    description = "Fast running between wickets! SKY turns a single into a brisk brace.",
                    fantasyImpact = "+2 pts (Suryakumar Yadav)"
                ),
                LiveBallEvent(
                    ball = "16.4",
                    runs = "4",
                    description = "FOUR! Delicate dab past short third man by Virat Kohli! 50 partnership up!",
                    fantasyImpact = "+5 pts (Virat Kohli)"
                )
            )
        ),
        CricketMatch(
            id = "match_csk_mi",
            tournament = "Indian T20 League • Match 28",
            matchType = "T20",
            team1 = "Chennai Super Kings",
            team1Short = "CSK",
            team1Color = 0xFFF59E0B,
            team2 = "Mumbai Indians",
            team2Short = "MI",
            team2Color = 0xFF0284C7,
            venue = "Wankhede Stadium, Mumbai",
            status = MatchStatus.UPCOMING,
            startTimeFormatted = "Today, 7:30 PM",
            countdownFormatted = "Starts in 42m",
            megaPrizePool = "₹1.00 Crore",
            team1Score = null,
            team2Score = null,
            statusSummary = "Lineups Announced • MI won the toss & chose to bowl",
            isLineupAnnounced = true,
            pitchReport = PitchReport(
                pitchType = "Red Soil pitch, heavy dew expected in 2nd innings",
                avgScore = 192,
                paceRatio = 58,
                spinRatio = 42,
                weather = "Warm & Humid, 29°C",
                tossAdvice = "Teams chasing have won 74% matches here"
            )
        ),
        CricketMatch(
            id = "match_eng_sa",
            tournament = "International T20 Series • 1st T20I",
            matchType = "T20",
            team1 = "England",
            team1Short = "ENG",
            team1Color = 0xFFDC2626,
            team2 = "South Africa",
            team2Short = "SA",
            team2Color = 0xFF16A34A,
            venue = "Lord's Cricket Ground, London",
            status = MatchStatus.UPCOMING,
            startTimeFormatted = "Tomorrow, 6:00 PM",
            countdownFormatted = "18h 12m",
            megaPrizePool = "₹50 Lakhs",
            team1Score = null,
            team2Score = null,
            statusSummary = "Squads confirmed • Mega Contest registrations open",
            isLineupAnnounced = false,
            pitchReport = PitchReport(
                pitchType = "Greentop with slope advantage for swing bowlers",
                avgScore = 164,
                paceRatio = 78,
                spinRatio = 22,
                weather = "Partly Cloudy, 17°C",
                tossAdvice = "Win toss and bowl first"
            )
        ),
        CricketMatch(
            id = "match_rcb_kkr",
            tournament = "Indian T20 League • Match 29",
            matchType = "T20",
            team1 = "Royal Challengers",
            team1Short = "RCB",
            team1Color = 0xFFB91C1C,
            team2 = "Kolkata Knights",
            team2Short = "KKR",
            team2Color = 0xFF7C3AED,
            venue = "M. Chinnaswamy Stadium, Bengaluru",
            status = MatchStatus.UPCOMING,
            startTimeFormatted = "Tomorrow, 8:00 PM",
            countdownFormatted = "1d 2h",
            megaPrizePool = "₹75 Lakhs",
            team1Score = null,
            team2Score = null,
            statusSummary = "High-scoring venue • Short boundary dimensions",
            isLineupAnnounced = false
        ),
        CricketMatch(
            id = "match_ind_eng_completed",
            tournament = "T20 World Championship • Semi-Final 1",
            matchType = "T20",
            team1 = "India",
            team1Short = "IND",
            team1Color = 0xFF1E40AF,
            team2 = "England",
            team2Short = "ENG",
            team2Color = 0xFFDC2626,
            venue = "Adelaide Oval, Australia",
            status = MatchStatus.COMPLETED,
            startTimeFormatted = "Yesterday",
            countdownFormatted = "COMPLETED",
            megaPrizePool = "₹2.00 Crores",
            team1Score = "196/5 (20.0 ov)",
            team2Score = "172/9 (20.0 ov)",
            statusSummary = "India won by 24 runs • Jasprit Bumrah 3/18 (4 ov)",
            isLineupAnnounced = true
        )
    )

    fun getPlayersForMatch(matchId: String): List<Player> {
        return when (matchId) {
            "match_ind_aus", "match_ind_eng_completed" -> indVsAusPlayers
            "match_csk_mi" -> cskVsMiPlayers
            else -> defaultCricketPlayers
        }
    }

    val indVsAusPlayers = listOf(
        // Wicket-Keepers (WK)
        Player("p_kl_rahul", "KL Rahul", "K. Rahul", "IND", PlayerRole.WK, 9.0, 342, 78.4, listOf(42, 68, 55, 30, 84), true, "RHB • Wicketkeeper"),
        Player("p_josh_inglis", "Josh Inglis", "J. Inglis", "AUS", PlayerRole.WK, 8.5, 290, 62.1, listOf(35, 48, 72, 12, 55), true, "RHB • Wicketkeeper"),
        Player("p_sanju_samson", "Sanju Samson", "S. Samson", "IND", PlayerRole.WK, 8.5, 260, 41.5, listOf(82, 14, 95, 28, 40), false, "RHB • Explosive"),

        // Batters (BAT)
        Player("p_virat_kohli", "Virat Kohli", "V. Kohli", "IND", PlayerRole.BAT, 10.5, 512, 92.6, listOf(78, 92, 45, 65, 88), true, "RHB • Anchor / King"),
        Player("p_rohit_sharma", "Rohit Sharma", "R. Sharma", "IND", PlayerRole.BAT, 10.0, 480, 88.2, listOf(62, 85, 30, 92, 54), true, "RHB • Hitman Captain"),
        Player("p_travis_head", "Travis Head", "T. Head", "AUS", PlayerRole.BAT, 9.5, 465, 85.0, listOf(98, 34, 82, 45, 76), true, "LHB • Power Opener"),
        Player("p_suryakumar", "Suryakumar Yadav", "S. Yadav", "IND", PlayerRole.BAT, 9.5, 430, 81.3, listOf(48, 72, 88, 32, 60), true, "RHB • 360 Degree"),
        Player("p_mitch_marsh", "Mitchell Marsh", "M. Marsh", "AUS", PlayerRole.BAT, 9.0, 380, 74.5, listOf(55, 42, 65, 38, 70), true, "RHB • Captain Power"),
        Player("p_david_warner", "David Warner", "D. Warner", "AUS", PlayerRole.BAT, 9.0, 360, 68.2, listOf(40, 52, 68, 22, 64), true, "LHB • Experienced"),
        Player("p_shubman_gill", "Shubman Gill", "S. Gill", "IND", PlayerRole.BAT, 9.0, 350, 65.0, listOf(38, 70, 45, 52, 35), true, "RHB • Elegant"),
        Player("p_tim_david", "Tim David", "T. David", "AUS", PlayerRole.BAT, 8.0, 220, 36.4, listOf(24, 38, 15, 45, 30), true, "RHB • Big Finisher"),

        // All-Rounders (AR)
        Player("p_hardik_pandya", "Hardik Pandya", "H. Pandya", "IND", PlayerRole.AR, 9.5, 475, 89.2, listOf(68, 75, 52, 88, 62), true, "RHB • Fast Medium"),
        Player("p_glenn_maxwell", "Glenn Maxwell", "G. Maxwell", "AUS", PlayerRole.AR, 9.5, 440, 83.5, listOf(85, 40, 92, 30, 68), true, "RHB • Off Break Spin"),
        Player("p_marcus_stoinis", "Marcus Stoinis", "M. Stoinis", "AUS", PlayerRole.AR, 9.0, 395, 75.8, listOf(50, 62, 35, 72, 45), true, "RHB • Medium Pacer"),
        Player("p_ravindra_jadeja", "Ravindra Jadeja", "R. Jadeja", "IND", PlayerRole.AR, 8.5, 360, 71.0, listOf(45, 55, 40, 60, 50), true, "LHB • Left Arm Spin"),
        Player("p_axar_patel", "Axar Patel", "A. Patel", "IND", PlayerRole.AR, 8.5, 340, 64.2, listOf(52, 38, 48, 65, 42), true, "LHB • Left Arm Spin"),

        // Bowlers (BOWL)
        Player("p_jasprit_bumrah", "Jasprit Bumrah", "J. Bumrah", "IND", PlayerRole.BOWL, 10.0, 520, 95.1, listOf(92, 85, 78, 105, 68), true, "Right Arm Fast Yorker"),
        Player("p_pat_cummins", "Pat Cummins", "P. Cummins", "AUS", PlayerRole.BOWL, 9.5, 440, 82.0, listOf(68, 74, 55, 80, 62), true, "Right Arm Fast"),
        Player("p_mitchell_starc", "Mitchell Starc", "M. Starc", "AUS", PlayerRole.BOWL, 9.0, 425, 79.5, listOf(75, 60, 85, 45, 70), true, "Left Arm Express Fast"),
        Player("p_arshdeep_singh", "Arshdeep Singh", "A. Singh", "IND", PlayerRole.BOWL, 8.5, 390, 73.2, listOf(60, 70, 55, 82, 48), true, "Left Arm Fast Swing"),
        Player("p_adam_zampa", "Adam Zampa", "A. Zampa", "AUS", PlayerRole.BOWL, 8.5, 385, 70.8, listOf(55, 65, 75, 40, 60), true, "Right Arm Leg Break"),
        Player("p_kuldeep_yadav", "Kuldeep Yadav", "K. Yadav", "IND", PlayerRole.BOWL, 8.5, 410, 76.4, listOf(70, 80, 62, 58, 75), true, "Left Arm Wrist Spin"),
        Player("p_josh_hazlewood", "Josh Hazlewood", "J. Hazlewood", "AUS", PlayerRole.BOWL, 8.5, 370, 66.5, listOf(50, 68, 45, 70, 55), true, "Right Arm Fast Seam")
    )

    val cskVsMiPlayers = listOf(
        // WK
        Player("p_ms_dhoni", "MS Dhoni", "MS Dhoni", "CSK", PlayerRole.WK, 9.0, 310, 82.5, listOf(45, 60, 32, 50, 40), true, "RHB • Legend Finisher"),
        Player("p_ishan_kishan", "Ishan Kishan", "I. Kishan", "MI", PlayerRole.WK, 9.0, 360, 76.2, listOf(58, 72, 34, 80, 45), true, "LHB • Wicketkeeper"),

        // BAT
        Player("p_ruturaj_gaikwad", "Ruturaj Gaikwad", "R. Gaikwad", "CSK", PlayerRole.BAT, 9.5, 460, 88.0, listOf(75, 82, 45, 90, 65), true, "RHB • CSK Captain"),
        Player("p_rohit_mi", "Rohit Sharma", "R. Sharma", "MI", PlayerRole.BAT, 10.0, 470, 89.4, listOf(80, 65, 42, 95, 60), true, "RHB • Power Opener"),
        Player("p_surya_mi", "Suryakumar Yadav", "S. Yadav", "MI", PlayerRole.BAT, 10.0, 490, 91.0, listOf(85, 92, 55, 70, 88), true, "RHB • Mr 360"),
        Player("p_shivam_dube", "Shivam Dube", "S. Dube", "CSK", PlayerRole.BAT, 9.0, 410, 80.2, listOf(68, 75, 38, 82, 55), true, "LHB • Monster Six Hitter"),
        Player("p_tilak_varma", "Tilak Varma", "T. Varma", "MI", PlayerRole.BAT, 8.5, 380, 72.0, listOf(52, 64, 45, 70, 58), true, "LHB • Middle Order"),
        Player("p_ajinkya_rahane", "Ajinkya Rahane", "A. Rahane", "CSK", PlayerRole.BAT, 8.0, 290, 45.0, listOf(35, 48, 25, 60, 30), true, "RHB • Classical"),

        // AR
        Player("p_hardik_mi", "Hardik Pandya", "H. Pandya", "MI", PlayerRole.AR, 9.5, 440, 86.0, listOf(70, 65, 58, 80, 62), true, "RHB • MI Captain"),
        Player("p_ravindra_csk", "Ravindra Jadeja", "R. Jadeja", "CSK", PlayerRole.AR, 9.0, 420, 84.5, listOf(60, 72, 45, 68, 55), true, "LHB • All Rounder"),
        Player("p_rachin_ravindra", "Rachin Ravindra", "R. Ravindra", "CSK", PlayerRole.AR, 8.5, 360, 69.0, listOf(55, 48, 70, 32, 60), true, "LHB • Left Arm Spin"),
        Player("p_moeen_ali", "Moeen Ali", "M. Ali", "CSK", PlayerRole.AR, 8.5, 320, 58.0, listOf(40, 50, 35, 65, 42), true, "LHB • Off Spin"),
        Player("p_tim_david_mi", "Tim David", "T. David", "MI", PlayerRole.AR, 8.0, 280, 48.0, listOf(30, 45, 20, 55, 38), true, "RHB • Finisher"),

        // BOWL
        Player("p_jasprit_mi", "Jasprit Bumrah", "J. Bumrah", "MI", PlayerRole.BOWL, 10.0, 530, 96.0, listOf(95, 88, 82, 110, 75), true, "Right Fast Death Specialist"),
        Player("p_matheesha_pathirana", "Matheesha Pathirana", "M. Pathirana", "CSK", PlayerRole.BOWL, 9.0, 430, 83.0, listOf(78, 65, 85, 50, 72), true, "Right Arm Slinger"),
        Player("p_gerald_coetzee", "Gerald Coetzee", "G. Coetzee", "MI", PlayerRole.BOWL, 8.5, 370, 68.0, listOf(58, 70, 45, 65, 50), true, "Right Arm Express"),
        Player("p_deepak_chahar", "Deepak Chahar", "D. Chahar", "CSK", PlayerRole.BOWL, 8.5, 350, 65.0, listOf(55, 62, 40, 75, 48), true, "Right Arm Swing"),
        Player("p_piyush_chawla", "Piyush Chawla", "P. Chawla", "MI", PlayerRole.BOWL, 8.0, 310, 52.0, listOf(42, 50, 38, 60, 45), true, "Leg Break Googly"),
        Player("p_tushar_deshpande", "Tushar Deshpande", "T. Deshpande", "CSK", PlayerRole.BOWL, 8.0, 320, 54.0, listOf(45, 55, 35, 68, 40), true, "Right Arm Medium Fast")
    )

    val defaultCricketPlayers = indVsAusPlayers

    fun getContestsForMatch(matchId: String): List<Contest> {
        return listOf(
            Contest(
                id = "${matchId}_mega",
                matchId = matchId,
                title = "Mega Contest • ₹1st Rank ₹25 Lakhs",
                category = ContestCategory.MEGA,
                totalPrizePool = "₹2,50,00,000",
                entryFee = 49,
                totalSpots = 750000,
                spotsFilled = 684200,
                firstPrize = "₹25,00,000",
                winnerPercentage = 65,
                maxTeamsPerUser = 20,
                isGuaranteed = true,
                prizeBreakup = listOf(
                    ContestPrizeBreakup("Rank 1", "₹25,00,000 + Trophy"),
                    ContestPrizeBreakup("Rank 2", "₹5,00,000"),
                    ContestPrizeBreakup("Rank 3", "₹2,00,000"),
                    ContestPrizeBreakup("Rank 4 - 10", "₹50,000 each"),
                    ContestPrizeBreakup("Rank 11 - 100", "₹10,000 each"),
                    ContestPrizeBreakup("Rank 101 - 1000", "₹1,000 each"),
                    ContestPrizeBreakup("Rank 1001 - 50000", "₹100 each")
                )
            ),
            Contest(
                id = "${matchId}_h2h_1",
                matchId = matchId,
                title = "Head to Head • Winner Takes ₹100",
                category = ContestCategory.HEAD_TO_HEAD,
                totalPrizePool = "₹100",
                entryFee = 59,
                totalSpots = 2,
                spotsFilled = 1,
                firstPrize = "₹100",
                winnerPercentage = 50,
                maxTeamsPerUser = 1,
                isGuaranteed = true,
                prizeBreakup = listOf(
                    ContestPrizeBreakup("Rank 1", "₹100")
                )
            ),
            Contest(
                id = "${matchId}_h2h_grand",
                matchId = matchId,
                title = "Pro Battle • 2 Members Only",
                category = ContestCategory.HEAD_TO_HEAD,
                totalPrizePool = "₹1,000",
                entryFee = 575,
                totalSpots = 2,
                spotsFilled = 1,
                firstPrize = "₹1,000",
                winnerPercentage = 50,
                maxTeamsPerUser = 1,
                isGuaranteed = true,
                prizeBreakup = listOf(
                    ContestPrizeBreakup("Rank 1", "₹1,000")
                )
            ),
            Contest(
                id = "${matchId}_wta_4",
                matchId = matchId,
                title = "Winner Takes All • 4 Spots",
                category = ContestCategory.WINNER_TAKES_ALL,
                totalPrizePool = "₹660",
                entryFee = 199,
                totalSpots = 4,
                spotsFilled = 3,
                firstPrize = "₹660",
                winnerPercentage = 25,
                maxTeamsPerUser = 1,
                isGuaranteed = true,
                prizeBreakup = listOf(
                    ContestPrizeBreakup("Rank 1", "₹660")
                )
            ),
            Contest(
                id = "${matchId}_mini_mega",
                matchId = matchId,
                title = "Mini Mega Contest • ₹10 Lakhs",
                category = ContestCategory.MEGA,
                totalPrizePool = "₹10,00,000",
                entryFee = 25,
                totalSpots = 55000,
                spotsFilled = 48200,
                firstPrize = "₹1,00,000",
                winnerPercentage = 60,
                maxTeamsPerUser = 10,
                isGuaranteed = true,
                prizeBreakup = listOf(
                    ContestPrizeBreakup("Rank 1", "₹1,00,000"),
                    ContestPrizeBreakup("Rank 2", "₹25,000"),
                    ContestPrizeBreakup("Rank 3 - 5", "₹10,000 each"),
                    ContestPrizeBreakup("Rank 6 - 50", "₹1,000 each")
                )
            ),
            Contest(
                id = "${matchId}_practice",
                matchId = matchId,
                title = "Free Practice League • Level Up Skills",
                category = ContestCategory.PRACTICE,
                totalPrizePool = "Bragging Rights",
                entryFee = 0,
                totalSpots = 10000,
                spotsFilled = 8420,
                firstPrize = "Fan11 Pro Badge + 50 XP",
                winnerPercentage = 50,
                maxTeamsPerUser = 3,
                isGuaranteed = true,
                prizeBreakup = listOf(
                    ContestPrizeBreakup("Rank 1", "Fan11 Champion Badge"),
                    ContestPrizeBreakup("Top 10%", "50 XP")
                )
            )
        )
    }

    val sampleLeaderboard = listOf(
        com.example.data.model.LeaderboardEntry(1, "KingKohli_XI (T1)", "Arjun Sharma", 428.5, 2, false, "₹25,00,000"),
        com.example.data.model.LeaderboardEntry(2, "BumrahBoom (T2)", "Rohit Verma", 419.0, -1, false, "₹5,00,000"),
        com.example.data.model.LeaderboardEntry(3, "StrikeForce (T1)", "Kunal Deshmukh", 412.5, 4, false, "₹2,00,000"),
        com.example.data.model.LeaderboardEntry(4, "My Dream 11 (T1)", "You (Fan11 Pro)", 398.0, 8, true, "₹50,000"),
        com.example.data.model.LeaderboardEntry(5, "AussieBlasters (T3)", "Steve M.", 392.5, -2, false, "₹50,000"),
        com.example.data.model.LeaderboardEntry(6, "CricketWizard (T1)", "Pooja Hegde", 387.0, 1, false, "₹50,000"),
        com.example.data.model.LeaderboardEntry(7, "HitmanHulk (T2)", "Manish Pandey", 381.5, -3, false, "₹50,000"),
        com.example.data.model.LeaderboardEntry(8, "SuperKings11 (T1)", "Vignesh R.", 376.0, 0, false, "₹50,000")
    )
}
