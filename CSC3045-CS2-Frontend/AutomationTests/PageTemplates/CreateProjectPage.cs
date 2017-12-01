using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using TestStack.White.UIItems;
using TestStack.White.UIItems.WindowItems;

namespace AutomationTests.PageTemplates
{
    public class CreateProjectPage : BasePage
    {
        public CreateProjectPage(Window window) : base(window) { }

        public override bool IsCurrentPage()
        {
            return PageTitle.Text.Equals("Create Project");
        }

        public TextBox ProjectNameTextBox
        {
            get { return MainWindow.Get<TextBox>("ProjectNameTextBox"); }
        }
        public TextBox ProjectDescriptionTextBox
        {
            get { return MainWindow.Get<TextBox>("ProjectDescriptionTextBox"); }
        }
        public Button CreateProjectButton
        {
            get { return MainWindow.Get<Button>("CreateButton"); }
        }
        public Button CancelButton
        {
            get { return MainWindow.Get<Button>("CancelButton"); }
        }
        public Button LogoutButton
        {
            get { return MainWindow.Get<Button>("LogoutButton"); }
        }
    }
}
