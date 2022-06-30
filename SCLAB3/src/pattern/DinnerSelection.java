package pattern;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import auxiliary.Dish;

public class DinnerSelection implements SelectionStrategy<List<Dish>>{
	//Map对应菜品和分数
	private Map<Dish,Integer> Dishes ;
	//要选出的菜品数量
	private int number = 0;
	
	public DinnerSelection(Map<Dish,Integer> Dishes, int number)
	{
		this.number = number;
		this.Dishes =new HashMap<Dish,Integer>(Dishes);
	}
	
	@Override
	public List<Dish> select() {
		if(number >= Dishes.size())
		{
			List<Dish> DishList = new LinkedList<Dish>();
			DishList.addAll(Dishes.keySet());
			return DishList;
		}
		else
		{
			//排序
			Map.Entry<Dish,Integer> temp;
			List<Map.Entry<Dish,Integer>> list = new LinkedList<Map.Entry<Dish,Integer>>(Dishes.entrySet());
			for(int i = list.size()-1 ; i > 0 ; i--)
			{
				for(int j = 0; j < i; j++)
				{
					if(list.get(j).getValue().intValue() < list.get(j+1).getValue().intValue())
					{
						temp = list.get(j);
						list.set(j,list.get(j+1));
						list.set(j+1,temp);
					}
				}
			}
			//返回最高分的几个菜
			List<Dish> DishList = new LinkedList<Dish>();
			for(int i = 0; i < number;i++)
			{
				DishList.add(list.get(i).getKey());
			}
			return DishList;
		}
	}
}
