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
    
    public partial class Register : Page
    {
        private AuthenticationClient client;
       
        public Register()
        {
            InitializeComponent();
            client = new AuthenticationClient();
        }

        /// <summary>
        /// Method for Creating The action that occurs on button click, calls the checkValidationMethod, if
        /// it returns true then attempt to register to the backend. If the response returned is equal to 
        /// Succesfully registered (ie 200) then create a new page, if its not
        /// there will be further feedback indicating what is wrong, also caught is the server not started error
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        public void  RegisterButton_Click(Object sender, EventArgs e)
        {
         
            if (checkValidation() ) {
                try
                {
                    string result = this.client.Register(
                        UsernameTextBox.Text, 
                        PasswordTextBox.Password.ToString(),
                        FirstnameTextBox.Text, 
                        SurnameTextBox.Text, 
                        EmailTextBox.Text,
                        ProductOwnerCheckBox.IsChecked.Value,
                        ScrumMasterCheckBox.IsChecked.Value,
                        DeveloperCheckBox.IsChecked.Value
                    );
                    MessageBox.Show(result);

                    if (result == "Succesfully Registered")
                    {
                        Page test = new test();
                        NavigationService.GetNavigationService(this).Navigate(test);
                    }


                }
                catch (RestResponseErrorException ex)
                {
                    MessageBox.Show(ex.Message+"Server Not Available Please Try Again Later");
                }

            }

        }
        /// <summary>
        /// Method for checking if the required values for the User Registration Form is filled in
        /// If the Form isnt filled correctly, text box is highlighted
        /// </summary>
        /// <param name="a"></param>
        /// <returns></returns>

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
        /// <summary>
        /// Method for checking if password isnt empty, if it is turn the  box red
        /// </summary>
        /// <param name="a"></param>
        /// <returns></returns>

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
        /// <summary>
        /// check the 2 passwords match 
        /// </summary>
        /// <param name="a"></param>
        /// <param name="b"></param>
        /// <returns></returns>

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
        /// <summary>
        /// Method to Check validation, returns true if all conditions are met,
        /// false otherwise
        /// </summary>
        /// <returns></returns>


        public Boolean checkValidation()
        {
           return checkRequiredValues(UsernameTextBox)&&
            checkRequiredValues(FirstnameTextBox) &&
            checkRequiredValues(EmailTextBox) &&
            checkPasswordNotEmpty(PasswordTextBox) &&
            checkPasswordNotEmpty(ConfirmPasswordTextBox) &&
            checkPasswordsMatch(PasswordTextBox, ConfirmPasswordTextBox);
        }
    }
}
