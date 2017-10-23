using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;
using RestSharp;
using CSC3045_CS2.Exception;
using CSC3045_CS2.Service;

namespace CSC3045_CS2.Pages
{
    /// <summary>
    /// Interaction logic for Register.xaml
    /// </summary>
    public partial class test: Page
    {
        private RegisterClient client;
       
        public test()
        {
            InitializeComponent();
            client = new RegisterClient();
        }
        public void  RegisterButton_Click(Object sender, EventArgs e)
        {
            

           
         

        }

        public Boolean checkRequiredValues(TextBox a)
        {
            if (a.Text == "")
            {
                a.Background = Brushes.Red;
                return false;
            }
            else
            {
                a.Background = Brushes.White;
                return true;
            }
            
            
        }
        public Boolean checkPasswordNotEmpty(PasswordBox a)
        {
            if (a.Password.ToString()== "")
            {
              a.Background = Brushes.Red;
              return false;
            }
            else
            {
                a.Background = Brushes.White;
                return true;
            }
            
            
        }
        public Boolean checkPasswordsMatch(PasswordBox a, PasswordBox b)
        {
            
            if (a.Password.ToString() != b.Password.ToString())
            {
                String text = "Passwords Dont Match";
                MessageBox.Show(text);
                a.Background = Brushes.Red;
                b.Background = Brushes.Red;

                return false;
            }
            else
            {
                a.Background = Brushes.White;
                return true;
            }
             
        }
        
        
    }
}
