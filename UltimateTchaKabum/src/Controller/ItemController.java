package Controller;

import java.util.ArrayList;

import Model.CardSprite;

public class ItemController {

	/*
	 * >>>>>>>>> ITENS <<<<<<<<<
	 * Itens Normais:
	 *  1 - Estourar certos da tela
	 *  2 - Freeze 
	 *  3 - Combo x2 (dura 30seg)
	 *  4 - Nuvens
	 *  5 - Visualizar cores certas
	 * 
	 * Itens Especiais: 
	 * 101 - Second Chance (eterno) 
	 * 102 - Combo x2 (eterno) 
	 * 103 - Baloes de vida (dificeis de subirem) 
	 * 104 - Baloes de resetar o jogo (muito dificeis de subirem)
	 * 105 - Miracle (reseta o jogo mantendo a pontuaçao e combo, depois de usado nao aparece mais durante a quele jogo)
	 * 106 - Coins x2 (eterno)
	 */
	private ArrayList<CardSprite> itensCards = new ArrayList<CardSprite>();
	private int index = 0;
	
	
	public ItemController(){
	}
	
	public void AddCard(CardSprite card){
		itensCards.add(card);
	}
	
	public CardSprite getNextCard(){
		if((index + 1) == itensCards.size()){
			return itensCards.get(0);
		}else{
			return itensCards.get(index+1);
		}
		
	}
	
	public void increaseCardIndex(){
		if((index + 1) == itensCards.size()){
			index = 0;
		}else{
			index++;
		}
	}
	
	public void decreaseCardIndex(){
		if(index == 0){
			index = (itensCards.size() - 1);
		}else{
			index = (index-1);
		}
	}
	public CardSprite getPrevCard(){
		if(index == 0){
			return itensCards.get(itensCards.size() - 1);
		}else{
			return itensCards.get(index-1);
		}
		
	}
	
	public CardSprite getActualCard(){
		return itensCards.get(index);
	}
	
	public CardSprite getByIndex(int index){
		int i = 0;
		for(int cont = 0; cont < itensCards.size(); cont++){
			if(itensCards.get(cont).getItem().getId() == index){
				i = cont;
			}
		}
		return itensCards.get(i);
	}
	
	public int getIndex(){
		return index;
	}
	
}
