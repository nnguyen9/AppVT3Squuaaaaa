class CreateUsers < ActiveRecord::Migration
  def change
    create_table :users do |t|
    	t.string :first_name
    	t.string :last_name
    	t.string :cap_id
    	t.string :phone
    	t.string :password_hash

      t.timestamps null: false
    end
  end
end
