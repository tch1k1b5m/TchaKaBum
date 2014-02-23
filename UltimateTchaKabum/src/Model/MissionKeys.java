package Model;

public class MissionKeys {
	
	// Chaves de balões
	public static final int Yellow = 1;
	public static final int DarkBlue = 2;
	public static final int Red = 3;
	public static final int Green = 4;
	public static final int Gray = 5;
	public static final int Black = 6;
	public static final int Pink = 7;
	public static final int LightBlue = 8;
	public static final int Orange = 9;
	
	// Chaves de itens especiais
	public static final int BalloonWDM = 23;
	public static final int ItemColorReminder = 24;
	public static final int ItemSecondChance = 25;
	public static final int ItemTargetRepeater = 26;
	public static final int CloudBlock = 28;
	public static final int Joker = 21;
	public static final int CoinBalloon = 10;
	public static final int BlasterItem = 11;
	public static final int BlizzardBalloon = 12;
	public static final int ComboItem = 13;
	public static final int AddLifeItem = 14;
	
	//Chaves de Collect
	public static final int CollectGold = 15;
	public static final int Combo = 16;
	public static final int Points = 17;
	public static final int LevelChallenge = 22;
	public static final int LevelGenius = 27;
	
	// Chaves de Player Condition
	public static final int SurviveWithOneLife = 18;
	public static final int SurviveWithTwoLife = 19;
	public static final int SurviveWithThreeLife = 20;
	
	
	//-------------------------------------
	//	Tipos de missões
	//-------------------------------------
	
	// Estourar qualquer balão normal
	public static final int PopAnyColorBalloonMKey = 100;
	// Estourar um tipo de balão ou usar um tipo de item
	public static final int SingleActionMKey = 101;
	// Estourar dois tipos de balão ou usar dois tipos de item
	public static final int DualActionsMKey = 102;
	// Coletar gold, fazer combo, pontos ou alcançar level
	public static final int CollectMKey = 103;
	// Passar y lvls seguidos com x vidas
	public static final int PlayerConditionMKey = 104;
}
