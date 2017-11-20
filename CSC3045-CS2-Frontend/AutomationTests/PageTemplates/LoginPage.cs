using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using TestStack.White.UIItems;
using TestStack.White.UIItems.WindowItems;

namespace AutomationTests.PageTemplates
{
    public class LoginPage : BasePage
    {
        public TextBox EmailTextBox
        {
            get { return MainWindow.Get<TextBox>("EmailTextBox"); }
        }
        public TextBox PasswordTextBox
        {
            get { return MainWindow.Get<TextBox>("PasswordBox"); }
        }
        public Button RegisterButton
        {
            get { return MainWindow.Get<Button>("RegisterButton"); }
        }
        public Button LoginButton
        {
            get { return MainWindow.Get<Button>("LoginButton"); }
        }

        public LoginPage(Window window) : base(window)
        {

        }

        public override bool IsCurrentPage()
        {
            return PageTitle.Text.Equals("Login");
        }
        
        public void Login(string email, string password)
        {
            EmailTextBox.Text = email;
            PasswordTextBox.Text = password;

            LoginButton.Click();
        }
    }
}
