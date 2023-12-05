import java.util.Random;
public class RPGcharecter {
    String name;
    int level;
    int maxHP;
    int currentHP;
    int maxMana;
    int currentMana;
    int runSpeed;
    int attack;
    int defense;
    int intelligence ;
    int atkBonus;
    int intBonus;
    int defBonus;
    Equipment equippedSword;
    Equipment equippedShield;
    Accessory equippedRing;
    Accessory equippedNecklace;


    public RPGcharecter(String name, int level, int maxHP, int maxMana, int runSpeed, int attack, int defense,int intelligence) {
        this.name = name;
        this.level = level;
        this.maxHP = maxHP;
        this.currentHP = maxHP;
        this.maxMana = maxMana;
        this.currentMana = maxMana;
        this.runSpeed = runSpeed;
        this.attack = attack;
        this.defense = defense;
        this.intelligence = intelligence ;
    }


    public static class WizardCharacter extends RPGcharecter {
        public WizardCharacter(String name, int level) {
            super(name, level, 50, 120, 7, 3, 5, 12);
        }

        public void levelUp() {
            super.levelUp();
            maxHP += 15 ;
            currentHP = maxHP;
            maxMana += 10;
            currentMana = maxMana;
            runSpeed += 1;
            attack += 1 ;
            intelligence += 1 ;
            defense += 2 ;
            updateRunSpeed();
        }
    }
    public static class MonkCharacter extends RPGcharecter {
        public MonkCharacter(String name, int level) {
            super(name, level, 80, 150, 10, 5, 8, 5);
        }

        public void levelUp() {
            super.levelUp();
            maxHP += 5;
            currentHP = maxHP;
            maxMana += 10;
            currentMana = maxMana;
            runSpeed += 1;
            attack += 1;
            intelligence += 2;
            defense += 1;
            updateRunSpeed();
        }
    }
    public void levelUp(){
        level++ ;
        System.out.println(name + " leveled up to level " + level + "!");
    }

    public void  EquipRing(Ring ring){
        equippedRing = ring ;
        Random random = new Random();
        int bonusType = random.nextInt(3);
        switch (bonusType) {
            case 0:
                atkBonus = random.nextInt(11); // Random value between 0 and 10
                break;
            case 1:
                intBonus = random.nextInt(11);
                break;
            case 2:
                defBonus = random.nextInt(11);
                break;
        }
        System.out.println(name + " have a ring equipped. ");
        if (atkBonus != 0) {
            attack += atkBonus ;
            System.out.println("!! Have attack Bonus: " + atkBonus);
        } else if (intBonus != 0) {
            intelligence += intBonus ;
            System.out.println("!! Have intelligence Bonus: " + intBonus);
        } else if (defBonus != 0) {
            defense += defBonus ;
            System.out.println("!! Have defense Bonus: " + defBonus);
        }
    }
    public void EquipNecklace(Necklace necklace){
        equippedNecklace = necklace ;
        maxHP += necklace.HPplus ;
        System.out.println((name + " have a necklace equipped. "));
    }

    public void EquipSword(Sword sword) {
        equippedSword = sword;
        attack += sword.damage;
        updateRunSpeed();
        System.out.println(name + " have a sword equipped.");
    }

    public void EquipShield(Shield shield) {
        equippedShield = shield;
        defense += shield.reducedamage;
        updateRunSpeed();
        System.out.println(name + " have a shield equipped.");
    }

    public void LevelupSword() {
        if (equippedSword != null) {
            equippedSword.Levelup() ;
            attack += equippedSword.getLevel();
            System.out.println(name + "'s sword has leveled up to level " + equippedSword.getLevel() + "!");
            updateRunSpeed();
        } else {
            System.out.println(name + " does not have a sword equipped.");
        }
    }

    public void LevelUpShield() {
        if (equippedShield != null) {
            equippedShield.Levelup();
            defense += equippedShield.getLevel();
            System.out.println(name + "'s shield has leveled up to level " + equippedShield.getLevel() + "!");
            updateRunSpeed();
        } else {
            System.out.println(name + " does not have a shield equipped.");
        }
    }
    public void LevelUpNecklace() {
        if (equippedNecklace != null) {
            equippedNecklace.Levelup();
            maxHP += equippedNecklace.getLevel();
            System.out.println(name + "'s necklace has leveled up to level " + equippedNecklace.getLevel() + "!");
            updateRunSpeed();
        } else {
            System.out.println(name + " does not have a necklace equipped.");
        }
    }

    public void ActionAttack(RPGcharecter target) {
        if (equippedSword != null) {
            System.out.println(name + " is attacking " + target.getName() + " with " + equippedSword.getName() + "!");
            int damage = calculateDamage();
            target.takeDamage(damage);
        } else {
            System.out.println(name + " does not have a sword equipped. Cannot attack.");
        }
    }

    private int calculateDamage() {
        int baseDamage = attack;
        if (equippedSword != null) {
            baseDamage += equippedSword.getLevel(); // Consider the sword's level for additional damage
        }
        return baseDamage;
    }

    public void takeDamage(int damage) {
        int effectiveDefense = defense;
        if (equippedShield != null) {
            effectiveDefense += equippedShield.getLevel(); // Consider the shield's level for additional defense
        }

        int actualDamage = Math.max(0, damage - effectiveDefense);
        currentHP -= actualDamage;

        if (currentHP < 0) {
            currentHP = 0;
        }
        System.out.println(name + " took " + actualDamage + " damage. Current HP: " + currentHP);
    }

    public void updateRunSpeed() {
        int swordRunSpeedEffect = (equippedSword != null) ? equippedSword.getRunSpeedEffect() : 0;
        int shieldRunSpeedEffect = (equippedShield != null) ? equippedShield.getRunSpeedEffect() : 0;
        runSpeed = Math.max(0, 10 - swordRunSpeedEffect - shieldRunSpeedEffect );
    }

    public String getName(){
        return  name ;
    }

    public void showStats() {
        System.out.println("-----------------------------");
        System.out.println("Character Stats for " + name + ":");
        System.out.println("Level: " + level);
        System.out.println("HP: " + currentHP + "/" + maxHP);
        System.out.println("Mana: " + currentMana + "/" + maxMana);
        System.out.println("Attack: " + attack);
        System.out.println("Intelligence: " + intelligence);
        System.out.println("Defense: " + defense);
        System.out.println("Run Speed: " + runSpeed);
        if (equippedSword != null) {
            System.out.println("Equipped Sword: " + equippedSword.getName() + " (Level " + equippedSword.getLevel() + ")");
        } else {
            System.out.println("Equipped Sword: None");
        }
        if (equippedShield != null) {
            System.out.println("Equipped Shield: " + equippedShield.getName() + " (Level " + equippedShield.getLevel() + ")");
        } else {
            System.out.println("Equipped Shield: None");
        }
        System.out.println("Equipped Ring: " + ((equippedRing != null) ? equippedRing.getName() : "None"));
        if (equippedNecklace != null) {
            System.out.println("Equipped Necklace: " + equippedNecklace.getName() + " (Level " + equippedNecklace.getLevel() + ")");
        } else {
            System.out.println("Equipped Necklace: None");
        }
        System.out.println("-----------------------------");
    }


    public static class Equipment {
        String name;
        int level;
        int runSpeedEffect;

        public Equipment(String name, int level, int runSpeedEffect) {
            this.name = name;
            this.level = level;
            this.runSpeedEffect = runSpeedEffect;
        }

        public int getLevel() {
            return level;
        }

        public int getRunSpeedEffect() {
            return runSpeedEffect;
        }

        public String getName() {
            return name;
        }

        public void Levelup() {
            level++;
        }

    }

    public static class Sword extends Equipment {
        int damage;

        public Sword(String name, int level, int damage, int runSpeedEffect) {
            super(name, level, runSpeedEffect);
            this.damage = damage;
        }
        public void Levelup() {
            level++;
            damage += 2;
        }
        public int getDamage() {
            return damage;
        }
    }

    public static class Shield extends Equipment {
        int reducedamage;

        public Shield(String name, int level, int reducedamage, int runSpeedEffect) {
            super(name, level, runSpeedEffect);
            this.reducedamage = reducedamage;
        }

        public void Levelup() {
            level++;
            reducedamage += 2;
        }

        public int getReducedamage() {
            return reducedamage;
        }
    }

    public static class Accessory {
        String name;
        int level;

        public Accessory(String name, int level) {
            this.name = name;
            this.level = level;
        }

        public int getLevel() {
            return level;
        }

        public String getName() {
            return name;
        }
        public void Levelup() {
            level++;
        }
    }

    public static class Ring extends Accessory {
        public Ring(String name, int level) {
            super(name, level);
        }
    }

    public static class Necklace extends Accessory {
        int HPplus;

        public Necklace(String name, int level, int HPplus) {
            super(name, level);
            this.HPplus = HPplus;
        }

        public void Levelup() {
            level++;
            HPplus += 10;
            //return level;
        }

        public int getHPplus() {
            return HPplus;
        }
    }

    public static class RPGmain {

        public static void main(String[] args) {
            Sword playerSword = new Sword("Great Sword", 1, 12,1);
            Shield playerShield = new Shield("Steel Shield", 1, 5,3);
            Necklace playerNecklace = new Necklace("Silver Necklace",1,20);
            Ring playerRing = new Ring("Gold Ring",1);

            //crete charecter
            RPGcharecter wizard = new WizardCharacter("wizard", 1);
            RPGcharecter monk = new MonkCharacter("monk", 1);
            wizard.showStats();
            monk.showStats();

            //use equipments
            wizard.EquipSword(playerSword);
            wizard.showStats();
            monk.EquipShield(playerShield);
            monk.showStats();

            //charecter levelUp
            wizard.levelUp();
            wizard.showStats();
            monk.levelUp();
            monk.showStats();

            //equipment levelup
            wizard.LevelupSword();
            monk.LevelupSword();
            monk.LevelUpShield();

            //use Accessory
            wizard.EquipRing(playerRing);
            monk.EquipRing(playerRing);
            monk.EquipNecklace(playerNecklace);

            //accessory levelup
            wizard.LevelUpNecklace();
            monk.LevelUpNecklace();

            //Action
            wizard.ActionAttack(monk);
            monk.ActionAttack(wizard);
        }

    }
}

