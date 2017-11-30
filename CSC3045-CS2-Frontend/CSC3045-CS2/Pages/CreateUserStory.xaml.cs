using CSC3045_CS2.Exception;
using CSC3045_CS2.Models;
using CSC3045_CS2.Service;
using CSC3045_CS2.Utility;
using System;
using System.Text;
using System.Text.RegularExpressions;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Input;
using System.Windows.Navigation;

namespace CSC3045_CS2.Pages
{
    /// <summary>
    /// Interaction logic for CreateUserStory.xaml
    /// </summary>
    public partial class CreateUserStory : BasePage
    {
        #region Private Variables

        private Project _currentProject;

        private UserStoryClient _client;

        private Style _invalidTextBoxStyle;
        private Style _validTextBoxStyle;

        private String _warningMessage;

        #endregion

        public CreateUserStory(Project project)
        {
            InitializeComponent();
            CurrentPage = this.Title;
            DataContext = this;

            _client = new UserStoryClient();
            _currentProject = project;

            PageSetup();
        }

        #region Class Methods

        private void PageSetup()
        {
            _invalidTextBoxStyle = FindResource("InvalidTextBox") as Style;
            _validTextBoxStyle = FindResource("DefaultTextBox") as Style;
        }

        private bool CheckFields()
        {
            bool valid = true;
            StringBuilder sb = new StringBuilder();

            if (!String.IsNullOrEmpty(StoryNameTextBox.Text))
            {
                StoryNameTextBox.Style = _validTextBoxStyle;
            }
            else
            {
                StoryNameTextBox.Style = _invalidTextBoxStyle;
                valid = false;
                sb.Append("You must enter a Story Name\n");
            }

            if (!String.IsNullOrEmpty(StoryMarketValueTextBox.Text))
            {
                StoryMarketValueTextBox.Style = _validTextBoxStyle;
            }
            else
            {
                StoryMarketValueTextBox.Style = _invalidTextBoxStyle;
                valid = false;
                sb.Append("You must enter a Market Value");
            }

            if (!String.IsNullOrEmpty(StoryDescriptionTextBox.Text))
            {
                StoryDescriptionTextBox.Style = _validTextBoxStyle;
            }
            else
            {
                StoryDescriptionTextBox.Style = _invalidTextBoxStyle;
                valid = false;
                sb.Append("You must enter a Story Description");
            }

            _warningMessage = sb.ToString();
            return valid;
        }

        private int SetMarketValue()
        {
            int marketValue;
            if (int.TryParse(StoryMarketValueTextBox.Text, out marketValue)) {
                return marketValue;
            }
            return int.MaxValue;
        }

        /// <summary>
        /// Performs Regex Validation live on the TextBox's as data is entered
        /// Ensures that only digits can be entered into the TextBox
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void NumberOnlyTextBoxValidation(object sender, TextCompositionEventArgs e)
        {
            Regex regex = new Regex("[^0-9]+");
            e.Handled = regex.IsMatch(e.Text);
        }

        #endregion

        #region Command Methods

        public ICommand CancelCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    Page productBacklogPage = new ProductBacklog(_currentProject);

                    NavigationService.GetNavigationService(this).Navigate(productBacklogPage);
                });
            }
        }

        public ICommand CreateCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    if (CheckFields())
                    {
                        int marketValue = SetMarketValue();

                        UserStory userStory = new UserStory(
                                StoryNameTextBox.Text,
                                StoryDescriptionTextBox.Text,
                                marketValue,
                                _currentProject);

                        try
                        {
                            _client.CreateUserStory(userStory);

                            CancelCommand.Execute(null);
                        }
                        catch (RestResponseErrorException ex)
                        {
                            MessageBoxUtil.ShowErrorBox(ex.Message);
                        }
                    }
                    else
                    {
                        MessageBoxUtil.ShowWarningBox(_warningMessage);
                    }
                });
            }
        }

        #endregion
    }
}
