package sk.tsystems.forum.entity.common;


import sk.tsystems.forum.service.jpa.JpaConnector;

public class rurka {
	
	public static void main(String[] args) {
		try(JpaConnector jpa= new JpaConnector())
		{
			System.out.println("non filtered");
			for(Class<?> ee: jpa.getMappedClasses())
				System.out.println("\t"+ee.getName());

			System.out.println("\nFiltered for "+BlockableEntity.class.getName());
			for(Class<?> ee: jpa.getMappedClasses(BlockableEntity.class))
				System.out.println("\t"+ee.getName());
			
		}
	}
	
}

